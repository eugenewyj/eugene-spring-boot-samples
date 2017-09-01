package com.eugenewyj.samples.mybatis.plugin;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * @since 2017/9/1
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginPlugin implements Interceptor {
    private Integer defaultPage; //默认页码
    private Integer defaultPageSize; //默认每页条数
    private Boolean defaultUseFlag; //默认是否启动插件
    private Boolean defaultCheckFlag; //默认是否检测当前页码的正确性

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = getUnProxyObject(invocation);
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        if (!checkSelect(boundSql.getSql())) { // 不是select语句。
            return invocation.proceed();
        }
        Object parameterObject = boundSql.getParameterObject();
        PageParam pageParam = getPageParam(parameterObject);
        if (pageParam == null) { //没有分页参数，不启用插件。
            return invocation.proceed();
        }
        //获取分页参数
        Boolean useFlag = Optional.ofNullable(pageParam.getUseFlag()).orElse(this.defaultUseFlag);
        if (!useFlag) {
            return invocation.proceed();
        }
        Integer pageNum = Optional.ofNullable(pageParam.getPage()).orElse(this.defaultPage);
        Integer pageSize = Optional.ofNullable(pageParam.getPageSize()).orElse(this.defaultPageSize);
        Boolean checkFlag = Optional.ofNullable(pageParam.getCheckFlag()).orElse(this.defaultCheckFlag);
        int total = getTotal(invocation, metaStatementHandler, boundSql);
        //回写总数到分页参数里
        setTotalToPageParam(pageParam, total, pageSize);
        //检查当前页码的有效性
        checkPage(checkFlag, pageNum, pageParam.getTotalPage());
        //修改sql
        return changeSQL(invocation, metaStatementHandler, boundSql, pageNum, pageSize);
    }

    /**
     * 修改当前查询的SQL
     * @param invocation
     * @param metaStatementHandler
     * @param boundSql
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Object changeSQL(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, Integer pageNum, Integer pageSize) throws Exception {
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        String newSql = "select * from (" + sql + ") $_paging_table limit ?, ?";
        metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        PreparedStatement ps = (PreparedStatement) invocation.proceed();
        int count = ps.getParameterMetaData().getParameterCount();
        ps.setInt(count - 1, (pageNum - 1) * pageSize);
        ps.setInt(count, pageSize);
        return ps;
    }

    private void checkPage(Boolean checkFlag, Integer pageNum, Integer totalPage) throws Exception {
        if (checkFlag && (pageNum > totalPage)) {
            throw new Exception("查询失败， 查询页码【" + pageNum + "】大于总页数【" + totalPage + "】!!");
        }
    }

    /**
     * 回写翻页参数。
     * @param pageParam
     * @param total
     * @param pageSize
     */
    private void setTotalToPageParam(PageParam pageParam, int total, Integer pageSize) {
        pageParam.setTotal(total);
        int totalPage = total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1);
        pageParam.setTotalPage(totalPage);
    }

    /**
     * 获取总数。
     * @param invocation
     * @param metaStatementHandler
     * @param boundSql
     * @return
     */
    private int getTotal(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql) throws Exception {
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        org.apache.ibatis.session.Configuration configuration = mappedStatement.getConfiguration();
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        String countSql = "select count(*) as total from (" + sql + ") $_paging";
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try {
            ps = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(configuration, countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            handler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return total;
    }

    /**
     * 分解分页参数，这里支持使用Map和@Param注解传递参数或者继承PageParam的POJO。
     * @param parameterObject
     * @return
     */
    private PageParam getPageParam(Object parameterObject) {
        if (parameterObject == null) {
            return null;
        }
        PageParam pageParam = null;
        if (parameterObject instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) parameterObject;
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                Object value = paramMap.get(key);
                if (value instanceof PageParam) {
                    return (PageParam) value;
                }
            }
        } else if (parameterObject instanceof PageParam) {
            pageParam = (PageParam) parameterObject;
        }
        return pageParam;
    }

    /**
     * 判断sql是否是select.
     * @param sql
     * @return
     */
    private boolean checkSelect(String sql) {
        String trimSql = sql.trim();
        int idx = trimSql.toLowerCase().indexOf("select");
        return idx == 0;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String strDefaultPage = properties.getProperty("default.page", "1");
        String strDefaultPageSize = properties.getProperty("default.pageSize", "50");
        String strDefaultUseFlag = properties.getProperty("default.useFlag", "false");
        String strDefaultCheckFlag = properties.getProperty("default.checkFlag", "false");
        this.defaultPage = Integer.parseInt(strDefaultPage);
        this.defaultPageSize = Integer.parseInt(strDefaultPageSize);
        this.defaultUseFlag = Boolean.parseBoolean(strDefaultUseFlag);
        this.defaultCheckFlag = Boolean.parseBoolean(strDefaultCheckFlag);
    }

    /**
     * 从代理对象中分离出真实对象。
     * @param invocation
     * @return
     */
    private StatementHandler getUnProxyObject(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object object = null;
        while (metaStatementHandler.hasGetter("h")) {
            object = metaStatementHandler.getValue("h");
        }
        if (object == null) {
            return statementHandler;
        }
        return (StatementHandler) object;
    }
}

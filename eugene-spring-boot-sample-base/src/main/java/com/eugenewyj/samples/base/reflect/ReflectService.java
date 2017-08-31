package com.eugenewyj.samples.base.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @since 2017/8/31
 */
public class ReflectService {
    /**
     * 示例方法
     * @param name
     */
    public void sayHello(String name) {
        System.err.println("hello " + name);
    }

    /**
     * 测试方法入口
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        // 通过反射实例化ReflectService类
        Object service = Class.forName(ReflectService.class.getName()).newInstance();
        // 获取sayHello方法。
        Method method = service.getClass().getMethod("sayHello", String.class);
        // 通过反射调用方法。
        method.invoke(service, "eugene");
    }
}

package com.eugenewyj.samples.base.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @since 2017/8/31
 */
public class HelloServiceHandler implements InvocationHandler {
    /**
     * 真实服务对象。
     */
    private Object target;

    /**
     * 绑定委托对象并返回一个代理类。
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        // 取的代理类， JDK代理需要提供接口。
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 通过代理对象调用方法首先进入这个方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("#########我是JDK动态代理#########");
        System.err.println("准备调用HelloService.sayHello方法");
        // 实际执行HelloServiceImpl的sayHello方法。
        Object result = method.invoke(target, args);
        System.err.println("结束调用HelloService.sayHello方法");
        return result;
    }

    /**
     * 程序入口。
     * @param args
     */
    public static void main(String[] args) {
        HelloServiceHandler helloHandler = new HelloServiceHandler();
        HelloService proxy = (HelloService) helloHandler.bind(new HelloServiceImpl());
        proxy.sayHello("eugene");
    }
}

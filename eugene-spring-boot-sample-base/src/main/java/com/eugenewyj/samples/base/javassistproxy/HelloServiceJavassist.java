package com.eugenewyj.samples.base.javassistproxy;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

/**
 * @since 2017/8/31
 */
public class HelloServiceJavassist {
    public void sayHello(String name) {
        System.err.println("hello " + name);
    }

    /**
     * 创建代理
     * @param clazz
     * @return
     */
    public static Object createProxy(Class clazz) throws IllegalAccessException, InstantiationException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        Class clazzProxy = proxyFactory.createClass();
        MethodHandler handler = (self, thisMethod, proceed, args) -> {
            System.err.println("#########我是Javassist代理#########");
            System.err.println("准备调用方法" + thisMethod.getName());
            Object result = proceed.invoke(self, args);
            System.err.println("结束调用方法" + thisMethod.getName());
            return result;
        };
        ProxyObject instance = (ProxyObject) clazzProxy.newInstance();
        instance.setHandler(handler);
        return instance;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        HelloServiceJavassist helloServiceJavassist = (HelloServiceJavassist) HelloServiceJavassist.createProxy(HelloServiceJavassist.class);
        helloServiceJavassist.sayHello("eugene");
    }
}

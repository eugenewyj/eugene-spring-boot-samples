package com.eugenewyj.samples.base.jdkproxy;

/**
 * @since 2017/8/31
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        System.err.println("hello " + name);
    }
}

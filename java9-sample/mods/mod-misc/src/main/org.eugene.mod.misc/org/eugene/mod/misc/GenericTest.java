package org.eugene.mod.misc;

import java.util.concurrent.Callable;

public class GenericTest {
    public static void main(String[] args) {
        // 泛型匿名类 JDK7, JDK8中实例化方法。
        Callable<Integer> c1 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        };

        // 泛型匿名类，JDK9中实例化方法，可以省略类型。
        Callable<Integer> c2 = new Callable<>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        };
    }
}

package org.eugene.mod.misc;

public class SafeVarargsTest {
    //JDK9允许 SafeVarargs 注解用在私有方法上。
    @SafeVarargs
    private <T> void print(T... args) {
        for (T arg : args) {
            System.out.println(arg);
        }
    }

    public static void main(String[] args) {
        SafeVarargsTest test = new SafeVarargsTest();
        test.print(1, 2, 3);
        test.print("a", "b", "c");
    }
}

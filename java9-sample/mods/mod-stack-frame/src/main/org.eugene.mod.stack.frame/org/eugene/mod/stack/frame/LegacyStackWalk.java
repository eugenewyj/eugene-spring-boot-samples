package org.eugene.mod.stack.frame;

import java.lang.reflect.InvocationTargetException;

public class LegacyStackWalk {
    public static void main(String[] args) {
        m1();
    }

    public static void m1() {
        m2();
    }

    public static void m2() {
        System.out.println("\nWithout using reflection: ");
        m3();
        try {
            System.out.println("\nUsing reflection:");
            LegacyStackWalk.class.getMethod("m3")
                    .invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void m3() {
        StackTraceElement[] frames = Thread.currentThread()
                .getStackTrace();
        for (StackTraceElement frame : frames) {
            System.out.println(frame.toString());
        }
    }

}

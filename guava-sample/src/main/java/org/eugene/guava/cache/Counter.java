package org.eugene.guava.cache;

public class Counter {
    static int couter = 0;
    public static int getCounter() {
        return couter++;
    }
}

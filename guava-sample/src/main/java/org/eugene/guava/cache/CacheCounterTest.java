package org.eugene.guava.cache;

public class CacheCounterTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println(CacheCounter.getCounter());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}

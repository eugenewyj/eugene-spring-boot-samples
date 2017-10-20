package org.eugene.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CacheCounter {
    static LoadingCache<Long, AtomicLong> count = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<Long, AtomicLong>() {
        @Override
        public AtomicLong load(Long key) throws Exception {
            System.out.println("Load call!");
            return new AtomicLong(0);
        }
    });

    static long limits = 10;
    static int counter = 0;

    public static synchronized int getCounter() throws ExecutionException {
        while (true) {
            //获取当前时间戳作为key
            Long currentSeconds = System.currentTimeMillis() / 1000;
            if (count.get(currentSeconds).getAndIncrement() > limits) {
                continue;
            }
            return counter++;
        }
    }
}

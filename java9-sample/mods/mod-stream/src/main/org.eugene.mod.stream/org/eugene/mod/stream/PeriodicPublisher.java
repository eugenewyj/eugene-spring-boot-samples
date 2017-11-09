package org.eugene.mod.stream;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class PeriodicPublisher {
    final static int MAX_SLEEP_DURATION = 3;

    final static Random sleepTimeGenerator = new Random();

    public static void main(String[] args) {
        SubmissionPublisher<Long> pub = new SubmissionPublisher<>();

        SimpleSubscriber s1 = new SimpleSubscriber("S1", 2);
        SimpleSubscriber s2 = new SimpleSubscriber("S2", 5);
        SimpleSubscriber s3 = new SimpleSubscriber("S3", 6);
        SimpleSubscriber s4 = new SimpleSubscriber("S4", 10);

        pub.subscribe(s1);
        pub.subscribe(s2);
        pub.subscribe(s3);

        subscribe(pub, s4, 2);

        Thread pubThread = publish(pub, 5);
        try {
            pubThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread publish(SubmissionPublisher<Long> pub, long count) {
        Thread thread = new Thread(() -> {
            for (long i = 0; i < count; i++) {
                pub.submit(i);
                PeriodicPublisher.sleep(i);
            }
        });
        thread.start();
        return thread;
    }

    private static void sleep(long i) {
        int sleepTime = sleepTimeGenerator.nextInt(MAX_SLEEP_DURATION) + 1;
        try {
            System.out.printf("Published %d. Sleeping for %d sec.%n", i, sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void subscribe(SubmissionPublisher<Long> pub, SimpleSubscriber sub, int delaySeconds) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(delaySeconds);
                pub.subscribe(sub);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

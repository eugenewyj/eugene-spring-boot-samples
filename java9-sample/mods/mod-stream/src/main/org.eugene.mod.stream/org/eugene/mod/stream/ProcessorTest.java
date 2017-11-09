package org.eugene.mod.stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class ProcessorTest {
    public static void main(String[] args) {
        CompletableFuture<Void> subTask = null;
        try (SubmissionPublisher<Long> pub = new SubmissionPublisher<>()) {
            SimpleSubscriber sub = new SimpleSubscriber("S1", 10);
            FilterProcessor<Long> filter = new FilterProcessor<>(n -> n % 2 == 0);
            pub.subscribe(filter);
            filter.subscribe(sub);
            LongStream.range(1L, 7L)
                    .forEach(pub::submit);
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

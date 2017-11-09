package org.eugene.mod.stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

public class NumberPrinter {
    public static void main(String[] args) {
        CompletableFuture<Void> subTask = null;
        try (SubmissionPublisher<Object> publisher = new SubmissionPublisher<>()) {
            System.out.println("Subscriber Buffer Size: " + publisher.getMaxBufferCapacity());
            subTask = publisher.consume(System.out::println);
            LongStream.range(1L, 6L)
                    .forEach(publisher::submit);
        }

        if (subTask != null) {
            try {
                subTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

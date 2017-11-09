package org.eugene.mod.stream;

import java.util.concurrent.Flow;

public class SimpleSubscriber implements Flow.Subscriber<Long> {

    private Flow.Subscription subscription;
    private final long maxCount;
    private String name = "Unknown";
    private long counter;

    public SimpleSubscriber(String name, long maxCount) {
        this.maxCount = maxCount<=0 ? 1: maxCount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        System.out.printf("%s subscribed with max count %d.%n", name, maxCount);
        subscription.request(maxCount);
    }

    @Override
    public void onNext(Long item) {
        counter++;
        System.out.printf("%s received %d.%n", name, item);
        if (counter >= maxCount) {
            System.out.printf("Cancelling %s.Processed item count: %d.%n", name, counter);
            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.printf("An error ocurred in %s:%s.%n", name, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.printf("%s is complete.%n", name);
    }
}

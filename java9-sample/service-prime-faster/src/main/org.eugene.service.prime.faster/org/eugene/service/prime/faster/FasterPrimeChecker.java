package org.eugene.service.prime.faster;

import org.eugene.service.prime.PrimeChecker;

public class FasterPrimeChecker implements PrimeChecker {
    private static final String PROVIDER_NAME = "eugene.faster.primechecker";

    private FasterPrimeChecker() {
    }

    public static PrimeChecker provider() {
        return new FasterPrimeChecker();
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        long limit = (long) Math.sqrt(n);
        for (long i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

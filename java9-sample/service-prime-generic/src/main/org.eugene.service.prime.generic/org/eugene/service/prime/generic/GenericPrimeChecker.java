package org.eugene.service.prime.generic;

import org.eugene.service.prime.PrimeChecker;

public class GenericPrimeChecker implements PrimeChecker {

    public static final String PROVIDER_NAME = "eugene.generic.primechecker";

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (long i = 3; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

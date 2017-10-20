package org.eugene.service.prime.probable;

import org.eugene.service.prime.PrimeChecker;

import java.math.BigInteger;

public class ProbablePrimeChecker {
    // A provider method
    public static PrimeChecker provider() {
        final String PROVIDER_NAME = "eugene.probable.primechecker";
        return new PrimeChecker() {
            @Override
            public boolean isPrime(long n) {
                // Use 1000 for high certainty, which is an arbitrary big number I chose
                int certainty = 1000;
                return BigInteger.valueOf(n).isProbablePrime(certainty);
            }
            @Override
            public String getName() {
                return PROVIDER_NAME;
            }
        };
    }
}

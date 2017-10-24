package org.eugene.service.prime.client;

import org.eugene.service.prime.PrimeChecker;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        long[] numbers = {3, 4, 121, 977};
        try {
            PrimeChecker checker = PrimeChecker.newInstance();
            checkPrimes(checker, numbers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            PrimeChecker checker = PrimeChecker.newInstance("eugene.faster.primechecker");
            checkPrimes(checker, numbers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            PrimeChecker checker = PrimeChecker.newInstance("eugene.probable.primechecker");
            checkPrimes(checker, numbers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<PrimeChecker> checkerList = PrimeChecker.startsWith("org.eugene.service.prime");
        for (PrimeChecker checker : checkerList) {
            try {
                checkPrimes(checker, numbers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void checkPrimes(PrimeChecker checker, long[] numbers) {
        System.out.printf("Using %s:%n", checker.getName());
        for (long number : numbers) {
            if (checker.isPrime(number)) {
                System.out.printf("%d is a prime.%n", number);
            } else {
                System.out.printf("%d is not a prime.%n", number);
            }
        }
    }

}

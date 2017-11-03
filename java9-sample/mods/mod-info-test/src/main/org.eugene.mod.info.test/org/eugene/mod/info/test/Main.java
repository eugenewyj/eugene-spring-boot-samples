package org.eugene.mod.info.test;

import org.eugene.mod.info.UpdateModule;
import org.eugene.service.prime.PrimeChecker;

public class Main {
    public static void main(String[] args) {
        long[] numbers = {3, 10};
        try {
            PrimeChecker primeChecker = UpdateModule.findFirstService(PrimeChecker.class);
            for (long number : numbers) {
                boolean prime = primeChecker.isPrime(number);
                System.out.printf("%d 是一个素数： %b%n", number, prime);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

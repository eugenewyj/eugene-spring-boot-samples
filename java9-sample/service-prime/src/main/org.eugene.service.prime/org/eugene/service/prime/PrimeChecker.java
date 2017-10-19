package org.eugene.service.prime;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface PrimeChecker {
    String getName();

    boolean isPrime(long n);

    static PrimeChecker newInstance() {
        return ServiceLoader.load(PrimeChecker.class).findFirst().orElseThrow(() -> new RuntimeException("No PrimeChecker service provider found."));
    }

    static PrimeChecker newInstance(String providerName) {
        ServiceLoader<PrimeChecker> loader = ServiceLoader.load(PrimeChecker.class);
        for (PrimeChecker checker : loader) {
            if (checker.getName().equals(providerName)) {
                return checker;
            }
        }
        throw new RuntimeException("A PrimeChecker service provoider with the name '" + providerName + "' was not found.");
    }

    static List<PrimeChecker> startsWith(String prefix) {
        return ServiceLoader.load(PrimeChecker.class).stream().filter((ServiceLoader.Provider p) -> p.type().getName().startsWith(prefix)).map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}

module org.eugene.service.prime.generic {
    requires org.eugene.service.prime;
    provides org.eugene.service.prime.PrimeChecker with org.eugene.service.prime.generic.GenericPrimeChecker;
}
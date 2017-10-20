package org.eugene.mod.person.test;

import org.eugene.mod.person.Person;

public class Main {
    public static void main(String[] args) {
        Person eugene = new Person(1001, "Eugene", "Wang");
        String city = eugene.getAddress().getCity();
        System.out.printf("Eugene lives in %s%n", city);
    }
}

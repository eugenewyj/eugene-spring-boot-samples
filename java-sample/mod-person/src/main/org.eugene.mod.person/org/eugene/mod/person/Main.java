package org.eugene.mod.person;

import org.eugene.mod.address.Address;

public class Main {
    public static void main(String[] args) {
        Person eugene = new Person(1001, "Eugene", "Wang");
        String firstName = eugene.getFirstName();
        String lastName = eugene.getLastName();
        Address address = eugene.getAddress();
        System.out.printf("%s %s%n", firstName, lastName);
        System.out.printf("%s%n", address.getLine1());
        System.out.printf("%s, %s %s%n", address.getCity(), address.getState(), address.getZip());
    }
}

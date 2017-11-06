package org.eugene.mod.collection;

import java.util.Set;

public class SetTest {
    public static void main(String[] args) {
        Set<Integer> emptySet = Set.of();
        Set<Integer> luckyNumber = Set.of(19);
        Set<String> vowels = Set.of("A", "E", "I", "O", "U");
        System.out.println("emptySet = " + emptySet);
        System.out.println("singletonSet = " + luckyNumber);
        System.out.println("vowels = " + vowels);

        try {
            Set<Integer> set = Set.of(1, 2, null, 3);
        } catch (Exception e) {
            System.out.println("Nulls not allowed in Set.of().");
        }

        try {
            Set<Integer> set = Set.of(1, 2, 3, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            luckyNumber.add(8);
        } catch (Exception e) {
            System.out.println("Cannot add an element.");
        }

        try {
            luckyNumber.remove(0);
        } catch (Exception e) {
            System.out.println("Cannot remove an element.");
        }
    }
}

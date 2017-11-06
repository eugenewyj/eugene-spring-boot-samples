package org.eugene.mod.collection;

import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List<Integer> emptyList = List.of();
        List<Integer> luckyNumber = List.of(19);
        List<String> vowels = List.of("A", "E", "I", "O", "U");
        System.out.println("emptyList = " + emptyList);
        System.out.println("singletonList = " + luckyNumber);
        System.out.println("vowels = " + vowels);

        try {
            List<Integer> list = List.of(1, 2, null, 3);
        } catch (Exception e) {
            System.out.println("Nulls not allowed in List.of().");
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

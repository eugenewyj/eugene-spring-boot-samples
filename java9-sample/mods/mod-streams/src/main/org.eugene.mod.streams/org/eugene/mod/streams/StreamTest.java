package org.eugene.mod.streams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        System.out.println("Using Stream.dropWhile() and Stream.takeWhile():");
        testDropWhileAndTakeWhile();
        System.out.println("\nUsing Stream.ofNullable():");
        testOfNullable();
        System.out.println("\nUsing Stream.iterator():");
        testIterator();
    }

    private static void testIterator() {
        List<Integer> list = Stream.iterate(1, n -> n <= 10, n -> n + 1)
                .collect(Collectors.toList());
        System.out.println("Integers from 1 to 10:" + list);
    }

    private static void testOfNullable() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, null);
        map.put(4, "Four");
        Set<String> nonNullValues = map.entrySet()
                .stream()
                .flatMap(e -> Stream.ofNullable(e.getValue()))
                .collect(Collectors.toSet());
        System.out.println("Map:" + map);
        System.out.println("Non-null Values in Map:" + nonNullValues);
    }

    private static void testDropWhileAndTakeWhile() {
        List<Integer> list = List.of(1, 3, 5, 4, 6, 7, 8, 9);
        System.out.println("Original Stream:" + list);
        List<Integer> list2 = list.stream()
                .dropWhile(n -> n % 2 == 1)
                .collect(Collectors.toList());
        System.out.println("After using droWhile(n -> n % 2 == 1):" + list2);
        List<Integer> list3 = list.stream()
                .takeWhile(n -> n % 2 == 1)
                .collect(Collectors.toList());
        System.out.println("After using takeWhile(n -> n % 2 == 1):" + list3);
    }
}

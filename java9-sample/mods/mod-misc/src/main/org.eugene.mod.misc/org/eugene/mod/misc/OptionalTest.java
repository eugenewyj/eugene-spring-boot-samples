package org.eugene.mod.misc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalTest {
    public static void main(String[] args) {
        List<Optional<Integer>> optionalList = List.of(Optional.of(1),
                Optional.empty(),
                Optional.of(2),
                Optional.empty(),
                Optional.of(3));
        System.out.println("原始列表：" + optionalList);
        optionalList.stream()
                .forEach(p -> p.ifPresentOrElse(System.out::println, () -> System.out.println("Empty")));

        optionalList.stream()
                .map(p -> p.or(() -> Optional.of(0)))
                .forEach(System.out::println);

        List<Integer> list8 = optionalList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        System.out.println("Java 8 列表：" + list8);

        List<Integer> list9 = optionalList.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        System.out.println("Java 9 列表：" + list9);
    }
}

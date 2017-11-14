package org.eugene.mod.misc;

import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class ScannerTest {
    public static void main(String[] args) {
        String patternString = "\\b\\w+\\b";
        String input = "A test string,\n which contains a new line.";
        List<String> words = new Scanner(input)
                .findAll(patternString)
                .map(MatchResult::group)
                .collect(Collectors.toList());
        System.out.println("Input:" + input);
        System.out.println("Words:" + words);
    }
}

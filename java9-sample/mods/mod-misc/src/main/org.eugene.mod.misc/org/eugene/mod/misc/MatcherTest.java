package org.eugene.mod.misc;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MatcherTest {
    public static void main(String[] args) {
        String regex = "\\b(\\d{3})?(\\d{3})(\\d{4})\\b";
        String input = "1, 3342229999, 2330001, 6159996666, 123, 3340909090";

        Matcher matcher = Pattern.compile(regex)
                .matcher(input);

        List<String> phones = matcher.results()
                .map(mr -> (mr.group(1) == null ? "" : "(" + mr.group(1) + ") ") + mr.group(2) + "-" + mr.group(3))
                .collect(Collectors.toList());
        System.out.println("phones: " + phones);

        matcher.reset();

        Set<String> areaCodes = matcher.results()
                .filter(matchResult -> matchResult.group(1) != null)
                .map(matchResult -> matchResult.group(1))
                .collect(Collectors.toSet());
        System.out.println("Distinct Area Codes: " + areaCodes);
    }
}

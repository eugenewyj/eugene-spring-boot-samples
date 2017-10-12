package book.java9byexample.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @since 2017/10/12
 */
public class SimplestStringListSortTest {
    @Test
    public void canSortStrings() {
        ArrayList actualNames = new ArrayList(Arrays.asList("Johnson", "Wilson", "Wilkinson", "Abraham", "Dagobert"));
        Collections.sort(actualNames);
        Assertions.assertIterableEquals(new ArrayList<String>(Arrays.<String>asList("Abraham", "Dagobert", "Johnson", "Wilkinson", "Wilson")), actualNames);
    }
}

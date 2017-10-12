package java9.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java9不可修改list相关示例。
 * @since 2017/10/12
 */
public class ImmutableListTest {
    @Test
    public void testImmutableListNotModified() {
        List<String> list = List.of("Papaya", "Raisin", "Dried Apple", "Dried Peach");
        Assertions.assertEquals(4, list.size());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            list.add("Wheat");
            list.remove(1);
        });
    }

    @Test
    public void testImmutableListMutableElements() {
        Map<Integer, String> numbersMap = new HashMap<>();
        numbersMap.put(1, "One");
        numbersMap.put(2, "Two");

        Map<Integer, String> currencyMap = new HashMap<>();
        currencyMap.put(100, "Hunderds");
        currencyMap.put(1000, "Thousands");

        List<Map<Integer, String>> numCurrList = List.of(numbersMap, currencyMap);
        Assertions.assertEquals(2, numCurrList.get(0).size());

        numbersMap.put(3, "Three");
        numCurrList.get(0).put(4, "Four");
        Assertions.assertEquals(4, numCurrList.get(0).size());
    }

    @Test
    public void testImmutableListNullAttempts() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            List<String> stringList = List.of(null);
        });
    }

    @Test
    public void testImmutableListOrdered() {
        List<?> coffeeList = List.of("Espresso", "Latte", "Cappuccino");

        Assertions.assertEquals("Espresso", coffeeList.get(0));
        Assertions.assertEquals(2, coffeeList.indexOf("Cappuccino"));
    }

    @Test
    public void testImmutableListSerializable() throws IOException {
        List<String> drinks = List.of("Coffee", "Wine", "Fruit");
        ObjectOutputStream oos = new ObjectOutputStream(System.out);
        oos.writeObject(drinks);

        List<OutputStream> outputStreamList = List.of(new ByteArrayOutputStream(), new PrintStream(System.out));
        Assertions.assertThrows(NotSerializableException.class, () -> {
            oos.writeObject(outputStreamList);
        });
    }
}

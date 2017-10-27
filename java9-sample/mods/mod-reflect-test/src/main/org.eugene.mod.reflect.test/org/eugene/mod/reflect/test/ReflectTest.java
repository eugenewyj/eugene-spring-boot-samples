package org.eugene.mod.reflect.test;

import java.lang.reflect.Field;

public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.eugene.mod.reflect.Item");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            printFiledValue(field);
        }
    }

    private static void printFiledValue(Field field) {
        String fieldName = field.getName();
        try {
            field.setAccessible(true);
            System.out.println(fieldName + " = " + field.get(null));
        } catch (Exception e) {
            System.out.println("Accessing " + fieldName + ".Error: " + e.getMessage());
        }
    }
}

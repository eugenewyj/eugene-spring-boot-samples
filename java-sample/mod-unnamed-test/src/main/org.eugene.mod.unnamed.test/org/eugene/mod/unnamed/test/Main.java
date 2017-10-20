package org.eugene.mod.unnamed.test;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName("org.eugene.mod.reflect.Item");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName() + " = " + field.get(null));
        }
    }
}

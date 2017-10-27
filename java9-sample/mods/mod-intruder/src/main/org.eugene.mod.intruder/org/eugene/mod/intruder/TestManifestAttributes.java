package org.eugene.mod.intruder;

import java.lang.reflect.Field;

public class TestManifestAttributes {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Class<Long> clazz = Long.class;
        Field serialVersionUID = clazz.getDeclaredField("serialVersionUID");
        serialVersionUID.setAccessible(true);
        long sUidValue = (long) serialVersionUID.get(null);
        System.out.println("Long.serialVersionUid=" + sUidValue);
    }
}

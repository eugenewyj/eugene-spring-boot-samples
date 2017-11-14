package org.eugene.mod.misc;

import java.util.Arrays;

public class ArrayComparison {
    public static void main(String[] args) {
        int[] a1 = {1, 2, 3, 4, 5};
        int[] a2 = {1, 2, 7, 4, 5};
        int[] a3 = {1, 2, 3, 4, 5};

        System.out.println("3个数组：");
        System.out.println("a1:" + Arrays.toString(a1));
        System.out.println("a2:" + Arrays.toString(a2));
        System.out.println("a3:" + Arrays.toString(a3));

        System.out.println("使用equals方法比较数组：");
        System.out.println("Arrays.equals(a1, a2):" + Arrays.equals(a1, a2));
        System.out.println("Arrays.equals(a1, a3):" + Arrays.equals(a1, a3));
        System.out.println("Arrays.equals(a1, 0, 2, a2, 0, 2):" + Arrays.equals(a1, 0, 2, a2, 0, 2));

        System.out.println("使用compare方法比较数据：");
        System.out.println("Arrays.compare(a1, a2):" + Arrays.compare(a1, a2));
        System.out.println("Arrays.compare(a2, a1):" + Arrays.compare(a2, a1));
        System.out.println("Arrays.compare(a1, a3):" + Arrays.compare(a1, a3));
        System.out.println("Arrays.compare(a1, 0, 2, a2, 0, 2):" + Arrays.compare(a1, 0, 2, a2, 0, 2));

        System.out.println("使用mismatch方法查找不匹配的元素：");
        System.out.println("Arrays.mismatch(a1, a2):" + Arrays.mismatch(a1, a2));
        System.out.println("Arrays.mismatch(a1, a3):" + Arrays.mismatch(a1, a3));
        System.out.println("Arrays.mismatch(a1, 0, 5, a2, 0, 1):" + Arrays.mismatch(a1, 0, 5, a2, 0, 1));
    }
}

package org.eugene.mod.misc;

public class UnderscoreTest {
    public static void main(String[] args) {
        // 下划线作为变量名，java8中是警告，java9中编译错误。
//        int _ = 19;
//        System.out.println("_ = " + _);

        // 下划线和其他字符组合是合法变量名
        final int FINGER_COUNT = 20;
        final String _prefix = "Sha";
    }
}

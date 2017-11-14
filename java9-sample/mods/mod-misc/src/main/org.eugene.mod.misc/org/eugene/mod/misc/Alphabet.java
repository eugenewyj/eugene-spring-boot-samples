package org.eugene.mod.misc;

public interface Alphabet {
    default boolean isAtOddPos(char c) {
        return getPos(c) % 2 == 1;
    }

    default boolean isAtEvenPos(char c) {
        return getPos(c) % 2 == 0;
    }

    // 接口私有方法，在JDK9才开始支持，以前版本会报编译错误。
    private int getPos(char c) {
        if (!Character.isLetter(c)) {
            throw new RuntimeException("不是一个字符：" + c);
        }
        char uc = Character.toUpperCase(c);
        int pos = uc - 64;
        return pos;
    }
}

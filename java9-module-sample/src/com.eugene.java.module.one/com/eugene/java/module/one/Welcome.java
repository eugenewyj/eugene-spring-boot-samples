package com.eugene.java.module.one;

public class Welcome {
    public static void main(String[] args) {
        System.out.println("欢迎来到Java9模块系统");
        // 打印模Welcome类所在的模块名。
        Class<Welcome> clazz = Welcome.class;
        Module module = clazz.getModule();
        String moduleName = module.getName();
        System.out.format("模块名： %s\n", moduleName);
    }
}

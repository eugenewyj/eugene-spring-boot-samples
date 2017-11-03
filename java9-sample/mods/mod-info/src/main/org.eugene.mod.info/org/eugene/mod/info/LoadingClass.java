package org.eugene.mod.info;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class LoadingClass {
    public static void main(String[] args) {
        loadClass("org.eugene.mod.simple.Welcome");
        loadClass("org.eugene.mod.simple.XYZ");
        String moduleName = "org.eugene.mod.simple";
        Optional<Module> m = ModuleLayer.boot().findModule(moduleName);
        if (m.isPresent()) {
            Module simpleModule = m.get();
            loadClass(simpleModule, "org.eugene.mod.simple.Welcome");
            loadClass(simpleModule, "org.eugene.mod.simple.XYZ");
        } else {
            System.out.println("模块：" + moduleName + " 未发现。请确定将模块添加到了模块路径中。");
        }
    }

    private static void loadClass(Module simpleModule, String s) {
        try {
            Class<?> aClass = Class.forName(simpleModule, s);
            if (aClass == null) {
                System.out.println("类未发现：" + s);
            } else {
                System.out.println("发现类：" + aClass.getName());
                instantiateClass(aClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadClass(String s) {
        try {
            Class<?> aClass = Class.forName(s);
            System.out.println("发现类：" + aClass.getName());
            instantiateClass(aClass);
        } catch (ClassNotFoundException e) {
            System.out.println("类未发现：" + s);
        }
    }

    private static void instantiateClass(Class<?> aClass) {
        try {
            Constructor<?> c = aClass.getConstructor();
            Object o = c.newInstance();
            System.out.println("实例化类：" + aClass.getName());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.out.println("类：" + aClass.getName() + ",没有无参数构造函数。");
        }
    }
}

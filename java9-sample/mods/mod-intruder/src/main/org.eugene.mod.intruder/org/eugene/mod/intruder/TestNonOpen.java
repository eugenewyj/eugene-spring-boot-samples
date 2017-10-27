package org.eugene.mod.intruder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestNonOpen {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        String className = "org.eugene.mod.address.Address";
        //获取类
        Class<?> clazz = Class.forName(className);
        //获取无参数构造函数
        Constructor constructor = clazz.getConstructor();
        //创建一个实例
        Object address = constructor.newInstance();
        //获取getLine1方法
        Method getLine1 = clazz.getMethod("getLine1");
        String line1 = (String) getLine1.invoke(address);
        System.out.println("反射调用getLine1方法，结果：" + line1);
        //获取line1实例变量
        Field line1Field = clazz.getDeclaredField("line1");
        line1Field.setAccessible(true);
        String line11 = (String) line1Field.get(address);
        System.out.println("反射调用line1实例变量，结果：" + line11);
    }
}

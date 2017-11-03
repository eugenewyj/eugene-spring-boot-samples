package org.eugene.mod.layer;

public class LayerInfo {
    private static final String VERSION = "1.0";

    static {
        System.out.println("加载 LayerInfo 版本 " + VERSION);
    }

    public void printInfo() {
        Class cls = this.getClass();
        ClassLoader loader = cls.getClassLoader();
        Module module = cls.getModule();
        String name = module.getName();
        ModuleLayer layer = module.getLayer();
        System.out.println("类版本：" + VERSION);
        System.out.println("类名称：" + cls.getName());
        System.out.println("类加载器：" + loader);
        System.out.println("模块名：" + name);
        System.out.println("层名：" + layer);
    }
}

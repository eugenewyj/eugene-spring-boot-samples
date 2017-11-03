package org.eugene.mod.layer.test;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class LayerTest {
    public static void main(String[] args) {
        String curPath = LayerTest.class.getResource("").getPath();
        String path = curPath.replace("mod-layer-test/target/classes/org/eugene/mod/layer/test/", "mod-layer/target/mod-layer-0.0.1-SNAPSHOT.jar");
        Set<String> rootModules = Set.of("org.eugene.mod.layer");
        ModuleLayer customLayer = createLayer(path, rootModules);
        ModuleLayer bootLayer = ModuleLayer.boot();
        testLayer(bootLayer);
        System.out.println();
        testLayer(customLayer);
    }

    private static void testLayer(ModuleLayer customLayer) {
        String moduleName = "org.eugene.mod.layer";
        String className = "org.eugene.mod.layer.LayerInfo";
        String methodName = "printInfo";
        try {
            Class<?> cls = customLayer.findLoader(moduleName).loadClass(className);
            Object obj = cls.getConstructor().newInstance();
            Method method = cls.getMethod(methodName);
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ModuleLayer createLayer(String path, Set<String> rootModules) {
        Path path1 = Paths.get(path);
        ModuleFinder beforeFinder = ModuleFinder.of(path1);
        ModuleFinder afterFinder = ModuleFinder.of();

        Configuration parentConfig = ModuleLayer.boot().configuration();
        Configuration config = parentConfig.resolve(beforeFinder, afterFinder, rootModules);

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ModuleLayer parentLayer = ModuleLayer.boot();
        ModuleLayer layer = parentLayer.defineModulesWithOneLoader(config, systemClassLoader);
        if (layer.modules().isEmpty()) {
            System.out.println("\n不能发现模块 " + rootModules + " 在 " + path);
        }
        return layer;
    }
}

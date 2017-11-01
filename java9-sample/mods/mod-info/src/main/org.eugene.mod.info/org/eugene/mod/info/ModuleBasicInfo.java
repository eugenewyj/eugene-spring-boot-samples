package org.eugene.mod.info;

import org.eugene.service.prime.PrimeChecker;

import java.lang.module.ModuleDescriptor;
import java.sql.Driver;
import java.util.Set;

public class ModuleBasicInfo {
    public static void main(String[] args) {
        //获取当前类的模块
        Class<ModuleBasicInfo> clazz = ModuleBasicInfo.class;
        Module module = clazz.getModule();
        //输出模块信息
        printInfo(module);
        System.out.printf("----------------------");
        printInfo(PrimeChecker.class.getModule());
        System.out.printf("----------------------");
        printInfo(Driver.class.getModule());
    }

    private static void printInfo(Module module) {
        String moduleName = module.getName();
        boolean named = module.isNamed();
        System.out.printf("模块名：%s%n", moduleName);
        System.out.printf("是否是命名模块：%b%n", named);

        ModuleDescriptor descriptor = module.getDescriptor();
        if (descriptor == null) {
            Set<String> packages = module.getPackages();
            System.out.printf("包：%s%n", packages);
            return;
        }

        Set<ModuleDescriptor.Requires> requires = descriptor.requires();
        Set<ModuleDescriptor.Exports> exports = descriptor.exports();
        Set<String> uses = descriptor.uses();
        Set<ModuleDescriptor.Provides> provides = descriptor.provides();
        Set<String> packages = descriptor.packages();

        System.out.printf("Requires: %s%n", requires);
        System.out.printf("Exports: %s%n", exports);
        System.out.printf("Uses: %s%n", uses);
        System.out.printf("Provides: %s%n", provides);
        System.out.printf("Packages: %s%n", packages);
    }
}

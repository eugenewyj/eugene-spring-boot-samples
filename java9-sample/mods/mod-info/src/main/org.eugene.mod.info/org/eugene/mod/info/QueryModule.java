package org.eugene.mod.info;

import java.sql.Driver;

public class QueryModule {
    public static void main(String[] args) {
        Class<QueryModule> cls = QueryModule.class;
        Module module = cls.getModule();
        Module javaSqlModule = Driver.class.getModule();
        boolean canReadJavaSql = module.canRead(javaSqlModule);
        boolean exportModuleInfoPkg = module.isExported("org.eugene.mod.info");
        boolean exportsModuleInfoPkgToJavaSql = module.isExported("org.eugene.mod.info", javaSqlModule);
        boolean openModuleInfoPkgToJavaSql = module.isOpen("org.eugene.mod.info", javaSqlModule);

        // Print module type and name
        System.out.printf("Named Module: %b%n", module.isNamed());
        System.out.printf("Module Name: %s%n", module.getName());
        System.out.printf("Can read java.sql? %b%n", canReadJavaSql);
        System.out.printf("Exports org.eugene.mod.info? %b%n", exportModuleInfoPkg);
        System.out.printf("Exports org.eugene.mod.info to java.sql? %b%n", exportsModuleInfoPkgToJavaSql);
        System.out.printf("Opens org.eugene.mod.info to java.sql? %b%n", openModuleInfoPkgToJavaSql);
    }
}

package org.eugene.mod.info;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

public class FindingModule {
    public static void main(String[] args) {
        String curPath = FindingModule.class.getResource("").getPath();
        String path = curPath.replace("classes/org/eugene/mod/info/", "mod-info-0.0.1-SNAPSHOT.jar");
        String path2 = curPath.replace("mod-info/target/classes/org/eugene/mod/info/", "mod-info-test/target/mod-info-test-0.0.1-SNAPSHOT.jar");
        Path mp1 = Paths.get(path);
        Path mp2 = Paths.get(path2);
        ModuleFinder mf = ModuleFinder.of(mp1, mp2);
        Set<ModuleReference> moduleReferences = mf.findAll();
        moduleReferences.forEach(FindingModule::printInfo);
    }

    private static void printInfo(ModuleReference moduleReference) {
        ModuleDescriptor descriptor = moduleReference.descriptor();
        Optional<URI> location = moduleReference.location();
        URI uri = null;
        if (location.isPresent()) {
            uri = location.get();
        }
        System.out.printf("模块： %s, 位置：%s%n", descriptor.name(), uri);
    }
}

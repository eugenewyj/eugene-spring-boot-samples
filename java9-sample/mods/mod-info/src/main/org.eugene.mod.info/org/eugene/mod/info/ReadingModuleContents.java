package org.eugene.mod.info;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;

public class ReadingModuleContents {
    public static void main(String[] args) {
        ModuleFinder mf = ModuleFinder.ofSystem();
        Optional<ModuleReference> omr = mf.find("java.base");
        ModuleReference moduleRef = omr.get();
        try (ModuleReader reader = moduleRef.open()) {
            Optional<ByteBuffer> byteBuffer = reader.read("java/lang/Object.class");
            byteBuffer.ifPresent(byteBuffer1 -> {
                System.out.println("Object.class Size:" + byteBuffer1.limit());
                reader.release(byteBuffer1);
            });
            System.out.println("\njava.base模块中有5个资源。");
            reader.list()
                    .limit(5)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

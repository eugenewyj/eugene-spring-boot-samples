package org.eugene.mod.info;

import java.util.ServiceLoader;

public class UpdateModule {
    public static <T> T findFirstService(Class<T> service) {
        Module m = UpdateModule.class.getModule();
        if (!m.canUse(service)) {
            m.addUses(service);
        }
        return ServiceLoader.load(service)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No service provider found for the service:" + service.getName()));
    }
}

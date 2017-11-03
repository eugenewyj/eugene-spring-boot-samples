package org.eugene.mod.info.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.MODULE, ElementType.TYPE})
public @interface Version {
    int major();

    int minor();
}

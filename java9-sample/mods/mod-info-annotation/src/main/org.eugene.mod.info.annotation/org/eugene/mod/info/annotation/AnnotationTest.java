package org.eugene.mod.info.annotation;

import java.lang.annotation.Annotation;

public class AnnotationTest {
    public static void main(String[] args) {
        Module m = AnnotationTest.class.getModule();
        Annotation[] annotations = m.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("注解：" + annotation);
        }

        Deprecated deprecated = m.getAnnotation(Deprecated.class);
        if (deprecated != null) {
            System.out.printf("废弃：since=%s, fromRemoval=%b%n", deprecated.since(), deprecated.forRemoval());
        }

        Version version = m.getAnnotation(Version.class);
        if (version != null) {
            System.out.printf("版本：major=%d, minor=%d%n", version.major(), version.minor());
        }
    }
}

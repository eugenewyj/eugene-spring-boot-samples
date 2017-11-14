package org.eugene.mod.misc;

public class ResourceTest {
    public static void main(String[] args) throws Exception {
        Resource r1 = new Resource(1);
        Resource r2 = new Resource(2);
        try (r1; r2) {
            r1.useIt();
            r2.useIt();
            r2.useIt();
        }

        useResource(new Resource(3));
    }

    private static void useResource(Resource resource) throws Exception {
        try (resource; Resource res4 = new Resource(4)) {
            resource.useIt();
            res4.useIt();
        }
    }
}

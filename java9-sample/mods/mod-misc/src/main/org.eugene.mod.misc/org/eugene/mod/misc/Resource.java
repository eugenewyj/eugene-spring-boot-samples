package org.eugene.mod.misc;

public class Resource implements AutoCloseable {
    private final long id;

    public Resource(long id) {
        this.id = id;
        System.out.printf("创建资源id=%d %n", this.id);
    }

    public void useIt() {
        System.out.printf("使用资源id=%d %n", this.id);
    }

    @Override
    public void close() throws Exception {
        System.out.printf("销毁资源id=%d %n", this.id);
    }
}

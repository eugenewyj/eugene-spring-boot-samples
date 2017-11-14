package org.eugene.mod.misc;

public class SpinWaitTest implements Runnable {
    private volatile boolean dataReady = false;

    @Override
    public void run() {
        while (!dataReady) {
            Thread.onSpinWait();
        }
        processData();
    }

    private void processData() {

    }

    public void setDataReady(boolean dataReady) {
        this.dataReady = dataReady;
    }
}

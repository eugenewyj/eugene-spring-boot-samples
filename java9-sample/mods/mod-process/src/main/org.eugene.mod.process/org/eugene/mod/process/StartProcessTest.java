package org.eugene.mod.process;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StartProcessTest {
    public static void main(String[] args) {
        Process p = Job.startProcess(5, 15);
        if (p == null) {
            System.out.println("Could not create a new process.");
            return;
        }
        ProcessHandle handle = p.toHandle();
        CurrentProcessInfo.printInfo(handle);
        CompletableFuture<ProcessHandle> future = handle.onExit();
        future.thenAccept(processHandle -> System.out.printf("Job (pid=%d) terminated.%n", processHandle.pid()));
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CurrentProcessInfo.printInfo(handle);
    }
}

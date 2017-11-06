package org.eugene.mod.process;

import java.util.concurrent.ExecutionException;

public class ManageProcessPermission {
    public static void main(String[] args) {
        long count = ProcessHandle.allProcesses().count();
        System.out.printf("Process Count: %d%n", count);

        Process p = Job.startProcess(1, 3);
        try {
            p.toHandle().onExit().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("A security manager is installed.");
        }

        try {
            count = ProcessHandle.allProcesses().count();
            System.out.printf("Process Count: %d%n", count);
        } catch (Exception e) {
            System.out.println("Could not get a process count:" + e.getMessage());
        }

        p = Job.startProcess(1, 3);
        try {
            p.toHandle().onExit().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

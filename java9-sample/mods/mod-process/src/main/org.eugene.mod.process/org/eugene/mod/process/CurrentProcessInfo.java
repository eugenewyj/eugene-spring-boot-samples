package org.eugene.mod.process;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CurrentProcessInfo {
    public static void main(String[] args) {
        ProcessHandle current = ProcessHandle.current();
        printInfo(current);
    }

    public static void printInfo(ProcessHandle current) {
        long pid = current.pid();
        boolean alive = current.isAlive();

        ProcessHandle.Info info = current.info();
        String command = info.command().orElse("");
        String[] args = info.arguments().orElse(new String[]{});
        String commandLine = info.commandLine().orElse("");
        ZonedDateTime startTime = info.startInstant().orElse(Instant.now()).atZone(ZoneId.systemDefault());
        Duration duration = info.totalCpuDuration().orElse(Duration.ZERO);
        String owner = info.user().orElse("Unknow");
        long childrenCount = current.children().count();

        System.out.printf("PID: %d%n", pid);
        System.out.printf("IsAlive: %b%n", alive);
        System.out.printf("Command: %s%n", command);
        System.out.printf("Argments: %s%n", args.toString());
        System.out.printf("CommandLine: %s%n", commandLine);
        System.out.printf("Start Time: %s%n", startTime);
        System.out.printf("CPU Time: %s%n", duration);
        System.out.printf("Owner: %s%n", owner);
        System.out.printf("Children Count: %d%n", childrenCount);
    }
}

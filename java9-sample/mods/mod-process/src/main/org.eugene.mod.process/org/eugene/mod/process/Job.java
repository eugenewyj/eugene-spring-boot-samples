package org.eugene.mod.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Job {
    public static final long DEFAULT_SLEEP_INTERVAL = 5;
    public static final long DEFAULT_SLEEP_DURATION = 60;

    public static void main(String[] args) {
        long sleepInterval = DEFAULT_SLEEP_INTERVAL;
        long sleepDuration = DEFAULT_SLEEP_DURATION;
        if (args.length >= 1) {
            sleepInterval = parseArg(args[0], DEFAULT_SLEEP_INTERVAL);
            if (sleepInterval < 0) {
                sleepInterval = 0;
            }
        }

        if (args.length >= 2) {
            sleepDuration = parseArg(args[1], DEFAULT_SLEEP_DURATION);
            if (sleepDuration < 0) {
                sleepDuration = 0;
            }
        }

        long pid = ProcessHandle.current().pid();
        System.out.printf("Job (pid=%d) info: Sleep Interval=%d seconds, Sleep Duration=%d seconds.%n", pid, sleepInterval, sleepDuration);
        for (long sleptFor = 0; sleptFor < sleepDuration; sleptFor += sleepInterval) {
            System.out.printf("Job (pid=%d) is going to sleep for %d seconds.%n", pid, sleepInterval);
            try {
                TimeUnit.SECONDS.sleep(sleepInterval);
            } catch (InterruptedException e) {
                System.out.printf("Job (pid=%d) was intterupted.%n", pid);
            }
        }
    }

    private static long parseArg(String arg, long defaultSleepDuration) {
        long value = defaultSleepDuration;
        if (arg != null) {
            try {
                value = Long.parseLong(arg);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static Process startProcess(long sleepInterval, long sleepDuration) {
        List<String> cmd = new ArrayList<>();
        addJvmPath(cmd);
        addModulePath(cmd);
        addClassPath(cmd);
        addMainClass(cmd);
        cmd.add(String.valueOf(sleepInterval));
        cmd.add(String.valueOf(sleepDuration));
        ProcessBuilder pb = new ProcessBuilder()
                .command(cmd)
                .inheritIO();
        String commandLine = pb.command()
                .stream()
                .collect(Collectors.joining(" "));
        System.out.println("Command used:\n" + commandLine);
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    private static void addMainClass(List<String> cmd) {
        Class<Job> cls = Job.class;
        String className = cls.getName();
        Module module = cls.getModule();
        if(module.isNamed()) {
            String moduleName = module.getName();
            cmd.add("--module");
            cmd.add(moduleName + "/" + className);
        } else {
            cmd.add(className);
        }
    }

    private static void addClassPath(List<String> cmd) {
        String classPath = System.getProperty("java.class.path");
        if(classPath != null && classPath.trim().length() > 0) {
            cmd.add("--class-path");
            cmd.add(classPath);
        }
    }

    private static void addModulePath(List<String> cmd) {
        String modulePath = System.getProperty("jdk.module.path");
        if(modulePath != null && modulePath.trim().length() > 0) {
            cmd.add("--module-path");
            cmd.add(modulePath);
        }
    }

    private static void addJvmPath(List<String> cmd) {
        String jvmPath = ProcessHandle.current()
                .info()
                .command().orElse("");
        if (jvmPath.length() > 0) {
            cmd.add(jvmPath);
        } else {
            String FILE_SEPARATOR = System.getProperty("file.separator");
            jvmPath = System.getProperty("java.home") +
                    FILE_SEPARATOR + "bin" +
                    FILE_SEPARATOR + "java";
            cmd.add(jvmPath);
        } 
    }
}

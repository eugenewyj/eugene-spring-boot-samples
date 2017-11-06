package org.eugene.mod.process;

import java.time.Duration;
import java.time.Instant;

public class ProcessStat {
    public static void main(String[] args) {
        System.out.printf("Longest CPU User Process:%n");
        ProcessHandle.allProcesses()
                .max(ProcessStat::compareCpuTime)
                .ifPresent(CurrentProcessInfo::printInfo);
        System.out.printf("%nLongest Running Process:%n");
        ProcessHandle.allProcesses()
                .max(ProcessStat::compareStartTime)
                .ifPresent(CurrentProcessInfo::printInfo);
    }

    private static int compareStartTime(ProcessHandle processHandle, ProcessHandle processHandle1) {
        return processHandle.info()
                .startInstant()
                .orElse(Instant.now())
                .compareTo(processHandle1.info()
                        .startInstant()
                        .orElse(Instant.now()));
    }

    private static int compareCpuTime(ProcessHandle processHandle, ProcessHandle processHandle1) {
        return processHandle.info()
                .totalCpuDuration()
                .orElse(Duration.ZERO)
                .compareTo(processHandle1.info()
                        .totalCpuDuration()
                        .orElse(Duration.ZERO));
    }
}

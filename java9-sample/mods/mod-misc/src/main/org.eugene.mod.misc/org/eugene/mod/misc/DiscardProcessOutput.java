package org.eugene.mod.misc;

import java.io.IOException;

public class DiscardProcessOutput {
    public static void main(String[] args) {
        System.out.println("使用 Redirect.INHERIT:");
        startProcess(ProcessBuilder.Redirect.INHERIT);
        System.out.println("使用 Redirect.DISCARD:");
        startProcess(ProcessBuilder.Redirect.DISCARD);
    }

    private static void startProcess(ProcessBuilder.Redirect outputDest) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder()
                    .command("java", "-version")
                    .redirectOutput(outputDest)
                    .redirectError(outputDest);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

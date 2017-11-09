package org.eugene.mod.stack.frame;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Stream;

public class StackWalking {
    public static void main(String[] args) {
        m1(Set.of());
    }

    public static void m1(Set<StackWalker.Option> options) {
        m2(options);
    }

    public static void m2(Set<StackWalker.Option> options) {
        try {
            System.out.println("Using StackWalker Options: " + options);
            StackWalking.class
                    .getMethod("m3", Set.class)
                    .invoke(null, options);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void m3(Set<StackWalker.Option> options) {
        StackWalker.getInstance(options)
                .walk(StackWalking::processStack);
    }

    public static <T> T processStack(Stream<StackWalker.StackFrame> stackFrameStream) {
        stackFrameStream.forEach(stackFrame -> {
            int bci = stackFrame.getByteCodeIndex();
            String className = stackFrame.getClassName();
            Class<?> classRef = null;
            try {
                classRef = stackFrame.getDeclaringClass();
            } catch (Exception e) {
            }
            String fileName = stackFrame.getFileName();
            int lineNumber = stackFrame.getLineNumber();
            String methodName = stackFrame.getMethodName();
            boolean nativeMethod = stackFrame.isNativeMethod();
            StackTraceElement stackTraceElement = stackFrame.toStackTraceElement();
            System.out.printf("Native Method=%b", nativeMethod);
            System.out.printf(", Byte Code Index=%d", bci);
            System.out.printf(", Module Name=%s", stackTraceElement.getModuleName());
            System.out.printf(", Module Version=%s", stackTraceElement.getModuleVersion());
            System.out.printf(", Class Name=%s", className);
            System.out.printf(", Class Reference=%s", classRef);
            System.out.printf(", File Name=%s", fileName);
            System.out.printf(", Line Number=%d", lineNumber);
            System.out.printf(", Method Name=%s.%n", methodName);
        });
        return null;
    }
}

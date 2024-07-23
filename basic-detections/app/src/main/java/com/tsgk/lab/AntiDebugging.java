package com.tsgk.lab;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class AntiDebugging {

    public static boolean isDeviceBeingDebugged(Context context) {
        return isDebugged() || isDebuggable(context) || detectDebuggerWithStackTrace() ||
                isDebuggerAttached();
    }

    public static boolean isDebugged() {
        return android.os.Debug.isDebuggerConnected() || android.os.Debug.waitingForDebugger();
    }

    public static boolean isDebuggable(Context context) {
        int applicationFlags = context.getApplicationInfo().flags;
        return (applicationFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static boolean detectDebuggerWithStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("com.android.tools.fd.runtime")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDebuggerAttached() {
        boolean isDebugged = false;
        try {
            java.lang.Process process = Runtime.getRuntime().exec(new String[]{"/system/bin/ps", "-A"});
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("gdbserver") || line.contains("gdb")) {
                    isDebugged = true;
                    break;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDebugged;
    }
}

package com.tsgk.lab;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class EmulatorDetection {

    public static boolean isEmulator() {
        return checkBuildProperties() || checkFiles()  || checkOperatorName();
    }

    private static boolean checkBuildProperties() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.HARDWARE.contains("vbox86")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
                || Build.BOARD.toLowerCase().contains("nox")
                || Build.BOOTLOADER.toLowerCase().contains("nox")
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.BRAND.toLowerCase().contains("nox")
                || Build.DEVICE.toLowerCase().contains("nox");
    }

    private static boolean checkFiles() {
        String[] knownEmulatorFiles = {
                "/dev/socket/qemud",
                "/dev/qemu_pipe",
                "/system/lib/libc_malloc_debug_qemu.so",
                "/sys/qemu_trace",
                "/system/bin/qemu-props",
                "/system/bin/androVM-prop",
                "/system/bin/microvirt-prop",
                "/system/bin/nox-prop",
                "/system/lib/libdroid4x.so",
                "/system/lib/libnox.so",
                "/system/lib/libdroid4x.so",
                "/system/lib/libldutils.so"
        };

        for (String file : knownEmulatorFiles) {
            if (new File(file).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkOperatorName() {
        String operatorName = getSystemProperty("gsm.operator.alpha");
        return (operatorName != null && !TextUtils.isEmpty(operatorName)
                && (operatorName.toLowerCase().contains("android")
                || operatorName.toLowerCase().contains("emulator")));
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (Exception ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignored) {
                }
            }
        }
        return line;
    }
}

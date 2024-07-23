package com.tsgk.lab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XposedDetection {

    private static final String[] XPOSED_FILES = {
            "/data/app/com.example.xposedinstaller-1/base.apk",
            "/system/xposed.prop",
            "/system/framework/XposedBridge.jar"
    };

    public static boolean detect() {
        boolean isXposedInstalled = checkXposedInstallation();
        boolean isXposedModInstalled = checkXposedModInstallation();
        boolean isXposedPropModified = checkXposedProp();

        if (isXposedInstalled || isXposedModInstalled || isXposedPropModified) {
            System.out.println("Xposed Framework detected!");
            return true;
        } else {
            System.out.println("Xposed Framework not detected.");
            return false;
        }
    }

    public static boolean checkXposedInstallation() {
        for (String filePath : XPOSED_FILES) {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("Found Xposed related file: " + filePath);
                return true;
            }
        }
        return false;
    }

    public static boolean checkXposedProp() {
        File xposedProp = new File("/system/xposed.prop");
        if (xposedProp.exists()) {
            Properties properties = new Properties();
            try (InputStream input = new java.io.FileInputStream(xposedProp)) {
                properties.load(input);
                String version = properties.getProperty("xposed.version");
                if (version != null && !version.isEmpty()) {
                    System.out.println("Found Xposed prop with version: " + version);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean checkXposedModInstallation() {
        // This method attempts to find evidence of Xposed mods.
        // Example approach: Checking for certain class modifications or specific hooks.

        try {
            // Using Reflection to check if specific Xposed hooks exist in the runtime
            Class<?> xposedBridgeClass = Class.forName("de.robv.android.xposed.XposedBridge");
            if (xposedBridgeClass != null) {
                System.out.println("Xposed Bridge class found in runtime.");
                return true;
            }
        } catch (ClassNotFoundException e) {
            // Xposed classes not found
            e.printStackTrace();
        }

        return false;
    }
}

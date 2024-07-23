package com.tsgk.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RootDetection {

    public static boolean isDeviceRooted() {
        return checkRootFiles() || checkRootPackages() || checkRootAccess();
    }

    private static boolean checkRootFiles() {
        List<String> pathsToCheck = new ArrayList<>();
        pathsToCheck.add("/system/app/Superuser.apk");
        pathsToCheck.add("/sbin/su");
        pathsToCheck.add("/system/bin/su");
        pathsToCheck.add("/system/xbin/su");
        pathsToCheck.add("/data/local/xbin/su");
        pathsToCheck.add("/data/local/bin/su");
        pathsToCheck.add("/system/sd/xbin/su");
        pathsToCheck.add("/system/bin/failsafe/su");
        pathsToCheck.add("/data/local/su");

        for (String path : pathsToCheck) {
            if (new File(path).exists()) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkRootPackages() {
        List<String> packagesToCheck = new ArrayList<>();
        packagesToCheck.add("com.noshufou.android.su");
        packagesToCheck.add("com.thirdparty.superuser");
        packagesToCheck.add("eu.chainfire.supersu");
        packagesToCheck.add("com.koushikdutta.superuser");
        packagesToCheck.add("com.zachspong.temprootremovejb");
        packagesToCheck.add("com.ramdroid.appquarantine");

        for (String packageName : packagesToCheck) {
            try {
                Class.forName(packageName);
                return true;
            } catch (ClassNotFoundException e) {
            }
        }

        return false;
    }

    private static boolean checkRootAccess() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}


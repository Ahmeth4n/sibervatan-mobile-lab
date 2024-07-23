package com.tsgk.lab;

import android.os.Build;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FridaDetection {

    public static boolean detectFrida() {
        return detectFridaServerRunning() || detectFridaByFiles();
    }

    private static boolean detectFridaServerRunning() {
        List<String> pathsToCheck = new ArrayList<>();
        pathsToCheck.add("/data/local/tmp/frida");
        pathsToCheck.add("/data/local/tmp/frida-server");
        pathsToCheck.add("/data/local/tmp/frida-inject");
        pathsToCheck.add("/data/local/tmp/re.frida.server");
        pathsToCheck.add("/data/local/tmp/re.frida.server-32");
        pathsToCheck.add("/data/local/tmp/re.frida.server-64");

        for (String path : pathsToCheck) {
            if (new File(path).exists()) {
                return true;
            }
        }

        return false;
    }

    private static boolean detectFridaByFiles() {
        List<String> filesToCheck = new ArrayList<>();
        filesToCheck.add("/system/lib/libfrida-gadget.so");
        filesToCheck.add("/system/lib64/libfrida-gadget.so");
        filesToCheck.add("/system/bin/frida-server");
        filesToCheck.add("/system/bin/frida");

        for (String filePath : filesToCheck) {
            if (new File(filePath).exists()) {
                System.out.println("Found suspicious file: " + filePath);
                return true;
            }
        }

        return false;
    }
}

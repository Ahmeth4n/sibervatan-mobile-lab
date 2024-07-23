#include "root_checker.h"
#include <jni.h>
#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include <sys/stat.h>

bool checkRootFiles() {
    std::vector<std::string> rootFiles = {
            "/system/bin/su",
            "/system/xbin/su",
            "/system/app/Superuser.apk",
            "/system/app/SuperSU.apk",
            "/system/xbin/daemonsu",
            "/system/etc/init.d/99SuperSUDaemon",
            "/system/bin/.ext/.su",
            "/system/usr/we-need-root/su-backup",
            "/system/xbin/mu"
    };

    for (const auto &file : rootFiles) {
        struct stat fileStat;
        if (stat(file.c_str(), &fileStat) == 0) {
            return true;
        }
    }
    return false;
}

bool checkRootCommands() {
    std::vector<std::string> rootCommands = {
            "which su",
            "which busybox"
    };

    for (const auto &command : rootCommands) {
        if (system(command.c_str()) == 0) {
            return true;
        }
    }
    return false;
}

JNIEXPORT jboolean JNICALL
Java_com_tsgk_lab_MainActivity_checkRoot(JNIEnv *env, jobject) {
    return (checkRootFiles() || checkRootCommands()) ? JNI_TRUE : JNI_FALSE;
}
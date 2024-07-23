#include "frida_checker.h"
#include <jni.h>
#include <dirent.h>
#include <string>
#include <vector>
#include <iostream>
#include <android/log.h>

#define APPNAME "FridaTSGK"
#define MAX_LINE 512


bool containsFridaFile(const std::string &fileName, const std::vector<std::string> &fridaFiles) {
    for (const auto &fridaFile : fridaFiles) {
        if (fileName.find(fridaFile) != std::string::npos) {
            return true;
        }
    }
    return false;
}

bool checkForFridaFiles() {
    std::vector<std::string> fridaFiles = {
            "frida-server", "frida-agent", "frida-inject", "frida-gadget","frida"
    };

    for (const auto& fileName : fridaFiles) {
        std::string filePath = "/data/local/tmp/" + fileName;
        FILE *fd = fopen(filePath.c_str(), "r");

        if (fd != NULL) {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Found file: %s", filePath.c_str());
            fclose(fd);
            return true;
        } else {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "File not found: %s", filePath.c_str());
        }
    }

    return false;

}


JNIEXPORT jboolean JNICALL
Java_com_tsgk_lab_MainActivity_checkFrida(JNIEnv *env, jobject) {
    bool result = checkForFridaFiles();
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "checkForFridaFiles returned: %s", result ? "true" : "false");
    return result ? JNI_TRUE : JNI_FALSE;
}
#include "frida_process_checker.h"
#include <dirent.h>
#include <string>
#include <vector>
#include <cstdio>
#include <cstring>
#include <android/log.h>

#define APPNAME "FridaProcessChecker"

// Frida process isimlerini kontrol eden fonksiyon
bool containsFridaProcess(const std::string &processName, const std::vector<std::string> &fridaProcesses) {
    for (const auto &fridaProcess : fridaProcesses) {
        if (processName.find(fridaProcess) != std::string::npos) {
            return true;
        }
    }
    return false;
}

// Frida processlerini kontrol eden fonksiyon
bool checkForFridaProcesses() {
    std::vector<std::string> fridaProcesses = {
            "frida-server", "frida-agent", "frida-inject", "frida-gadget"
    };

    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Opening /proc directory");

    DIR *procDir = opendir("/proc");
    if (procDir == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error opening /proc/: %s", strerror(errno));
        return false;
    }

    struct dirent *entry;
    while ((entry = readdir(procDir)) != nullptr) {
        if (entry->d_type == DT_DIR) {
            std::string dirName = entry->d_name;

            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Checking directory: %s", dirName.c_str());

            if (std::all_of(dirName.begin(), dirName.end(), ::isdigit)) {
                std::string cmdlinePath = "/proc/" + dirName + "/cmdline";

                __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Opening cmdline file: %s", cmdlinePath.c_str());

                // Open file using fopen
                FILE* cmdlineFile = fopen(cmdlinePath.c_str(), "r");
                if (cmdlineFile != nullptr) {
                    char cmdlineBuffer[1024];
                    size_t bytesRead = fread(cmdlineBuffer, 1, sizeof(cmdlineBuffer) - 1, cmdlineFile);
                    if (bytesRead > 0) {
                        cmdlineBuffer[bytesRead] = '\0'; // Null-terminate the string
                        std::string cmdline(cmdlineBuffer);

                        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Read cmdline: %s", cmdline.c_str());

                        if (containsFridaProcess(cmdline, fridaProcesses)) {
                            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Frida process found");
                            fclose(cmdlineFile);
                            closedir(procDir);
                            return true;
                        }
                    }
                    fclose(cmdlineFile);
                } else {
                    __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error opening cmdline file: %s", strerror(errno));
                }
            }
        }
    }

    closedir(procDir);
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Frida process not found");
    return false;
}

// JNI ile Java'dan çağrılacak fonksiyon
JNIEXPORT jboolean JNICALL
Java_com_tsgk_lab_MainActivity_checkFridaProcess(JNIEnv *env, jobject) {
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "checkFridaProcess() called");
    jboolean result = checkForFridaProcesses() ? JNI_TRUE : JNI_FALSE;
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "checkFridaProcess() returning %d", result);
    return result;
}

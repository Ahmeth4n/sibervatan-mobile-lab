#include "frida_library_checker.h"
#include <string>
#include <vector>
#include <cstdio>
#include <cstring>
#include <android/log.h>

#define APPNAME "FridaLibraryChecker"

// Frida kütüphanelerini kontrol eden fonksiyon
bool containsFridaLibrary(const std::string &mapsContent, const std::vector<std::string> &fridaLibraries) {
    for (const auto &fridaLibrary : fridaLibraries) {
        if (mapsContent.find(fridaLibrary) != std::string::npos) {
            return true;
        }
    }
    return false;
}

bool checkForFridaLibraries() {
    std::vector<std::string> fridaLibraries = {
            "libfrida", "frida"
    };

    std::string mapsPath = "/proc/self/maps";

    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Opening maps file: %s", mapsPath.c_str());

    // Open file using fopen
    FILE* mapsFile = fopen(mapsPath.c_str(), "r");
    if (mapsFile != nullptr) {
        char mapsBuffer[1024];
        std::string mapsContent;

        while (fgets(mapsBuffer, sizeof(mapsBuffer), mapsFile) != nullptr) {
            mapsContent += mapsBuffer;
        }

        fclose(mapsFile);

        __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Maps file content read");

        if (containsFridaLibrary(mapsContent, fridaLibraries)) {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Frida library found");
            return true;
        }
    } else {
        __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error opening %s: %s", mapsPath.c_str(), strerror(errno));
    }

    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Frida library not found");
    return false;
}

// JNI ile Java'dan çağrılacak fonksiyon
JNIEXPORT jboolean JNICALL
Java_com_tsgk_lab_MainActivity_checkFridaLibrary(JNIEnv *env, jobject) {
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "checkFridaLibrary() called");
    jboolean result = checkForFridaLibraries() ? JNI_TRUE : JNI_FALSE;
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "checkFridaLibrary() returning %d", result);
    return result;
}

#include "port_checker.h"
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <algorithm>
#include <android/log.h>

#define APPNAME "FridaDetection"
#define MAX_LINE 512


JNIEXPORT jboolean JNICALL Java_com_tsgk_lab_MainActivity_checkPort(JNIEnv *env, jobject, jint port) {
    FILE *file = fopen("/proc/net/tcp", "r");
    if (file == NULL) {
        __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error opening /proc/net/tcp: %s", strerror(errno));
        return JNI_FALSE;
    }

    char line[MAX_LINE];
    int line_num = 0;
    jboolean found = JNI_FALSE;

    while (fgets(line, sizeof(line), file) != NULL) {
        if (line_num == 0) {
            // Skip the header line
            line_num++;
            continue;
        }

        char local_address[128];
        unsigned int local_port;
        if (sscanf(line, "%*d: %64[0-9A-Fa-f]:%X %*64[0-9A-Fa-f]:%*X %*X %*X:%*X %*X:%*X %*X %*d %*d %*d %*d %*d %*d",
                   local_address, &local_port) == 2) {
            __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "Local address: %s, Local port: %u", local_address, local_port);
            if (local_port == 27042) {
                found = JNI_TRUE;
                break;
            }
        }

        line_num++;
    }

    fclose(file);
    return found;
}

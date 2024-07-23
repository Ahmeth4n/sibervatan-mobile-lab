#ifndef FRIDA_PROCESS_CHECKER_H
#define FRIDA_PROCESS_CHECKER_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_com_tsgk_lab_MainActivity_checkFridaProcess(JNIEnv *env, jobject);

#ifdef __cplusplus
}
#endif

#endif // FRIDA_PROCESS_CHECKER_H

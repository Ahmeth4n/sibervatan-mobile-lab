#ifndef FRIDA_CHECKER_H
#define FRIDA_CHECKER_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_com_tsgk_lab_MainActivity_checkFrida(JNIEnv *env, jobject);

#ifdef __cplusplus
}
#endif

#endif

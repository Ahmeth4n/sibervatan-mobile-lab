#ifndef ROOT_CHECKER_H
#define ROOT_CHECKER_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_com_tsgk_lab_MainActivity_checkRoot(JNIEnv *env, jobject);

#ifdef __cplusplus
}
#endif

#endif // ROOT_CHECKER_H

#include <jni.h>

#include <unistd.h>           /* for syscall() */
#include <sys/syscall.h>      /* for __NR_* definitions */

#include "com_github_marschall_getrandom_GetRandom.h"

static inline ssize_t getrandom(void *buf, size_t buflen, unsigned int flags)
{
  return syscall(__NR_getrandom, buf, buflen, flags);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_getrandom_GetRandom_getrandom0
  (JNIEnv *env, jclass clazz, jbyteArray bytes, jboolean r)
{
  return -1;
}

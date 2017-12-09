#include <jni.h>

#include <unistd.h>           /* for syscall() */
#include <sys/syscall.h>      /* for __NR_* definitions */
#include <stdlib.h>           /* for malloc and free */

#include "com_github_marschall_getrandom_GetRandom.h"

/*
 * The maximum size of the stack-allocated buffer.
 */
#define BUFFER_SIZE 8192

static inline ssize_t getrandom(void *buf, size_t buflen, unsigned int flags)
{
  return syscall(__NR_getrandom, buf, buflen, flags);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_getrandom_GetRandom_getrandom0
  (JNIEnv *env, jclass clazz, jbyteArray bytes, jboolean r)
{
  _Static_assert (sizeof(jbyte) == sizeof(char), "sizeof(jbyte) == sizeof(char)");

  jsize arrayLength = (*env)->GetArrayLength(env, bytes);
  char stackBuffer[BUFFER_SIZE];
  char *buffer = 0;
  
  if (arrayLength > BUFFER_SIZE)
  {

    buffer = malloc(sizeof(char) * (size_t) arrayLength);
    if (buffer == NULL) {
      return 0;
    }
  }
  else
  {
    buffer = stackBuffer;
  }
  
  if (arrayLength > BUFFER_SIZE)
  {
    free(buffer);
  }
  return -1;
}

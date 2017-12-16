#include <jni.h>

#include <unistd.h>           /* for syscall() */
#include <sys/syscall.h>      /* for __NR_* definitions */
#include <stdlib.h>           /* for malloc and free */
#include <errno.h>            /* errno */

#include "com_github_marschall_getrandom_Retrandom.h"

/*
 * The maximum size of the stack-allocated buffer.
 */
#define BUFFER_SIZE 2048

#define GRND_RANDOM 0x02

 _Static_assert (com_github_marschall_getrandom_Retrandom_EFAULT == EFAULT, "com_github_marschall_getrandom_Retrandom_EFAULT == EFAULT");
 _Static_assert (com_github_marschall_getrandom_Retrandom_EINTR == EINTR, "com_github_marschall_getrandom_Retrandom_EINTR == EINTR");
 _Static_assert (com_github_marschall_getrandom_Retrandom_EINVAL == EINVAL, "com_github_marschall_getrandom_Retrandom_EINVAL == EINVAL");

static inline ssize_t getrandom(void *buf, size_t buflen, unsigned int flags)
{
  return syscall(__NR_getrandom, buf, buflen, flags);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_getrandom_Retrandom_getrandom0
  (JNIEnv *env, jclass clazz, jbyteArray bytes, jboolean random)
{
  _Static_assert (sizeof(jbyte) == sizeof(char), "sizeof(jbyte) == sizeof(char)");

  jsize arrayLength = (*env)->GetArrayLength(env, bytes);
  char stackBuffer[BUFFER_SIZE];
  char *buffer = 0;
  unsigned int flags = 0;
  size_t bufferLength = sizeof(char) * (size_t) arrayLength;
  ssize_t written = 0;
  int getRandomErrorCode = 0;
  
  // set up buffer
  if (arrayLength > BUFFER_SIZE)
  {

    buffer = malloc(bufferLength);
    if (buffer == NULL) {
      return 0;
    }
  }
  else
  {
    buffer = stackBuffer;
  }
  
  if (random == JNI_TRUE)
  {
    flags |= GRND_RANDOM;
  }

  do
  {
    written += getrandom(buffer + written, bufferLength - (size_t) written, flags);
  }
  while (written != -1 && written < bufferLength);
 
  if (written == -1)
  {
    getRandomErrorCode = errno;
  }
  else
  {
    (*env)->SetByteArrayRegion(env, bytes, 0, arrayLength, (const jbyte *) buffer);
    if ((*env)->ExceptionCheck(env) == JNI_TRUE)
    {
      /* doens't really matter, ArrayIndexOutOfBoundsException will be thrown upon returning */
      written = -1;
      getRandomErrorCode = EFAULT;
    }
  }
  
  // clean up buffer if necessary
  if (arrayLength > BUFFER_SIZE)
  {
    free(buffer);
  }

  if (written == -1)
  {
    return getRandomErrorCode;
  }
  else
  {
    return 0;
  }
}

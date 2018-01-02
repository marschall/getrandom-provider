#include <jni.h>

#include <unistd.h>           /* for syscall() */
#include <sys/syscall.h>      /* for __NR_* definitions */
#include <stdlib.h>           /* for malloc and free */
#include <errno.h>            /* errno */
#include <string.h>           /* memset */

#include "com_github_marschall_getrandom_Getrandom.h"

/*
 * The maximum size of the stack-allocated buffer.
 */
#define BUFFER_SIZE 2048

#define GRND_RANDOM 0x02

 _Static_assert (com_github_marschall_getrandom_Getrandom_EFAULT == EFAULT, "com_github_marschall_getrandom_Getrandom_EFAULT == EFAULT");
 _Static_assert (com_github_marschall_getrandom_Getrandom_EINTR == EINTR, "com_github_marschall_getrandom_Getrandom_EINTR == EINTR");
 _Static_assert (com_github_marschall_getrandom_Getrandom_EINVAL == EINVAL, "com_github_marschall_getrandom_Getrandom_EINVAL == EINVAL");

static inline ssize_t getrandom(void *buf, size_t buflen, unsigned int flags)
{
  return syscall(__NR_getrandom, buf, buflen, flags);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_getrandom_Getrandom_getrandom0
  (JNIEnv *env, jclass clazz, jbyteArray bytes, jint arrayLength, jboolean random)
{
  _Static_assert (sizeof(jbyte) == sizeof(char), "sizeof(jbyte) == sizeof(char)");

  char stackBuffer[BUFFER_SIZE];
  char *buffer = 0;
  unsigned int flags = 0;
  size_t bufferLength = sizeof(char) * (size_t) arrayLength;
  ssize_t lastWritten = 0;
  ssize_t totalWritten = 0;
  int getRandomErrorCode = 0;
  
  /* set up buffer */
  if (arrayLength > BUFFER_SIZE)
  {
    buffer = malloc(bufferLength);
    if (buffer == NULL) {
      return com_github_marschall_getrandom_Getrandom_EMALLOCNULL;
    }
  }
  else
  {
    buffer = stackBuffer;
  }
  
  /* set up the flags */
  if (random == JNI_TRUE)
  {
    flags |= GRND_RANDOM;
  }

  /* call getrandom until we have all the bytes or a call fails */
  do
  {
    lastWritten = getrandom(buffer + totalWritten, bufferLength - (size_t) totalWritten, flags);
    totalWritten += lastWritten;
  }
  while (lastWritten != -1 && totalWritten < bufferLength);
 
  if (lastWritten == -1)
  {
    getRandomErrorCode = errno;
  }
  else
  {
    /* copy from native to Java memory */
    (*env)->SetByteArrayRegion(env, bytes, 0, arrayLength, (const jbyte *) buffer);

    /* error check */
    if ((*env)->ExceptionCheck(env) == JNI_TRUE)
    {
      /* doens't really matter, ArrayIndexOutOfBoundsException will be thrown upon returning */
      lastWritten = -1;
      getRandomErrorCode = EFAULT;
    }
  }
  
  /* clean out the native memory */
  memset(buffer, 0, (size_t) arrayLength);

  /* clean up buffer if necessary */
  if (arrayLength > BUFFER_SIZE)
  {
    free(buffer);
  }

  if (lastWritten == -1)
  {
    /* exception will be raised by calling Java code */
    return getRandomErrorCode;
  }
  else
  {
    return 0;
  }
}

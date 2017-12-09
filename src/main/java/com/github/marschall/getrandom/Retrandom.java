package com.github.marschall.getrandom;

import java.util.Objects;

final class Retrandom {

  static final boolean INITIALIZED;

  static {
    // -Djava.library.path=...
    String javaLibraryPath = System.getProperty("java.library.path");
    boolean initialized = false;
    try {
      Runtime.getRuntime().loadLibrary("getrandom-provider-0.1.0-SNAPSHOT");
      initialized = true;
      INITIALIZED = initialized;
    } catch (RuntimeException | Error e) {
      throw e;
    }
//    String libraryName = System.mapLibraryName("libgetrandom-provider-0.1.0-SNAPSHOT");
//    System.load("/home/marschall/Documents/workspaces/default/aioj/target/nar/aioj-0.1.0-SNAPSHOT-amd64-Linux-gpp-jni/lib/amd64-Linux-gpp/jni/" + libraryName);
  }

  static void assertInitialized() {
    if (!INITIALIZED) {
      throw new IllegalStateException("not initialized");
    }
  }

  private static final int EFAULT = 14;
  private static final int EINTR = 4;
  private static final int EINVAL = 22;

  private Retrandom() {
    throw new AssertionError("not instantiable");
  }

  static void getrandom(byte[] bytes, boolean random) {
    Objects.requireNonNull(bytes);
    int result = getrandom0(bytes, random);
    if (result != 0) {
      switch (result) {
        case EFAULT:

          break;
        case EINTR:

          break;
        case EINVAL:

          break;

        default:
          break;
      }
    }
  }


  // http://man7.org/linux/man-pages/man2/getrandom.2.html
  private static native int getrandom0(byte[] bytes, boolean random);

}

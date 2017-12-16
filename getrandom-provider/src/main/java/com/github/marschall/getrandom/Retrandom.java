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
//    String libraryName = System.mapLibraryName("libgetrandom-provider-0.1.0-SNAPSHOT.so");
//    System.load("/home/marschall/git/getrandom-provider/target/nar/getrandom-provider-0.1.0-SNAPSHOT-amd64-Linux-gpp-jni/lib/amd64-Linux-gpp/jni" + libraryName);
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
    int exitCode = getrandom0(bytes, random);
    if (exitCode != 0) {
      switch (exitCode) {
        case EFAULT:
          throw new IllegalStateException("The address referred to by buf is outside the accessible address space.");
        case EINTR:
          throw new IllegalStateException("The call was interrupted by a signal handler.");
        case EINVAL:
          throw new IllegalStateException("An invalid flag was specified in flags.");
        default:
          throw new IllegalStateException("Encountered unknown exit code: " + exitCode + ".");
      }
    }
  }


  // http://man7.org/linux/man-pages/man2/getrandom.2.html
  private static native int getrandom0(byte[] bytes, boolean random);

}

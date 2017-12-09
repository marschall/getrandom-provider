package com.github.marschall.getrandom;

import java.util.Objects;

final class Retrandom {

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

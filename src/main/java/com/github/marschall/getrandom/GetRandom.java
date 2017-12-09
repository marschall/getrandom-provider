package com.github.marschall.getrandom;

import java.security.SecureRandomSpi;
import java.util.Objects;

public class GetRandom extends SecureRandomSpi {

  @Override
  protected void engineSetSeed(byte[] seed) {
    Objects.requireNonNull(seed);
    // ignore

  }

  @Override
  protected void engineNextBytes(byte[] bytes) {
    Objects.requireNonNull(bytes);
    // TODO Auto-generated method stub

  }

  @Override
  protected byte[] engineGenerateSeed(int numBytes) {
    if (numBytes < 0) {
      throw new IllegalArgumentException("numBytes must not be negative");
    }
    byte[] bytes = new byte[numBytes];
    if (numBytes > 0) {
      this.engineNextBytes(bytes);
    }
    return bytes;
  }

  // http://man7.org/linux/man-pages/man2/getrandom.2.html
  private static native int getrandom0(byte[] bytes, boolean random);


}

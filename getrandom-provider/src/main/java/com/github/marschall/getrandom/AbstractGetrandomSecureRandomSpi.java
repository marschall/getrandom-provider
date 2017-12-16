package com.github.marschall.getrandom;

import java.security.SecureRandomSpi;
import java.util.Objects;

abstract class AbstractGetrandomSecureRandomSpi extends SecureRandomSpi {

  @Override
  protected void engineSetSeed(byte[] seed) {
    Objects.requireNonNull(seed);
    // ignore
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

}

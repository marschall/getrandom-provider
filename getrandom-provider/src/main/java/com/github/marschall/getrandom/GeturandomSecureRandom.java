package com.github.marschall.getrandom;

import java.util.Objects;

public class GeturandomSecureRandom extends AbstractGetrandomSecureRandomSpi {

  private static final long serialVersionUID = 1L;

  @Override
  protected void engineNextBytes(byte[] bytes) {
    Objects.requireNonNull(bytes);
    Retrandom.getrandom(bytes, false);
  }

}

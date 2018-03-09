package com.github.marschall.getrandom;

import java.security.SecureRandomSpi;
import java.util.Objects;

/**
 * A {@link SecureRandomSpi}s that uses the {@code getrandom} system call
 * with the /dev/urandom equivalent.
 */
public class GeturandomSecureRandom extends AbstractGetrandomSecureRandomSpi {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor for JCA, should not be called directly.
   */
  public GeturandomSecureRandom() {
    super();
  }

  @Override
  protected void engineNextBytes(byte[] bytes) {
    Objects.requireNonNull(bytes);
    Getrandom.getrandom(bytes, false);
  }

}

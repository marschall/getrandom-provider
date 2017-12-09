package com.github.marschall.getrandom;

import java.security.Provider;

/**
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/HowToImplAProvider.html">How to Implement a Provider in the Java Cryptography Architecture</a>
 *
 */
public final class GetrandomProvider extends Provider {

  private static final long serialVersionUID = 1L;

  public GetrandomProvider() {
    super("getrandom", 0.1d, "getrandom (SecureRandom)");
    this.put("SecureRandom.getrandom", GetrandomSecureRandom.class.getName());
    this.put("SecureRandom.geturandom", GeturandomSecureRandom.class.getName());
  }

}

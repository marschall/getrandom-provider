package com.github.marschall.getrandom;

import java.security.Provider;

/**
 * A security provider that installs two random number generation
 * algorithms that use the <a href="https://lwn.net/Articles/606141/">getrandom()</a>
 * system call.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/HowToImplAProvider.html">How to Implement a Provider in the Java Cryptography Architecture</a>
 */
public final class GetrandomProvider extends Provider {

  /**
   * The name of this security provider.
   */
  public static final String NAME = "getrandom";

  /**
   * The name algorithm that uses getrandom() with a blocking device (/dev/random).
   */
  public static final String GETRANDOM = "getrandom";

  /**
   * The name algorithm that uses getrandom() with a non-blocking device (/dev/urandom).
   */
  public static final String GETURANDOM = "geturandom";

  private static final long serialVersionUID = 1L;

  public GetrandomProvider() {
    super(NAME, 0.1d, "getrandom (SecureRandom)");
    this.put("SecureRandom." + GETRANDOM, GetrandomSecureRandom.class.getName());
    this.put("SecureRandom." + GETRANDOM + " ThreadSafe", "true");
    this.put("SecureRandom." + GETURANDOM, GeturandomSecureRandom.class.getName());
    this.put("SecureRandom." + GETURANDOM, GeturandomSecureRandom.class.getName());
    this.put("SecureRandom." + GETURANDOM + " ThreadSafe", "true");
  }

}

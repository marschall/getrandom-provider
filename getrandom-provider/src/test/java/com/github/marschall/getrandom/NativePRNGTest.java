package com.github.marschall.getrandom;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import org.junit.jupiter.api.Test;

class NativePRNGTest {

  @Test
  void nativePRNGNonBlocking() throws GeneralSecurityException {
    SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking", "SUN");
    byte[] bytes = new byte[32];
    random.nextBytes(bytes);
  }

  @Test
  void treadSafe() throws GeneralSecurityException {
    Provider sunProvider = Security.getProvider("SUN");
    String algorithm = sunProvider.getProperty("SecureRandom.NativePRNGNonBlocking");
    assertNotNull(algorithm);

    String threadSafe = sunProvider.getProperty("SecureRandom.NativePRNGNonBlocking ThreadSafe", "false");
    assertNotNull(threadSafe);
  }

}

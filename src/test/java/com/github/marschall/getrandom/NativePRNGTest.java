package com.github.marschall.getrandom;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import org.junit.jupiter.api.Test;

class NativePRNGTest {

  @Test
  void nativePRNGNonBlocking() throws GeneralSecurityException {
    SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking", "SUN");
    byte[] bytes = new byte[32];
    random.nextBytes(bytes);
  }

}

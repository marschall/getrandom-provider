package com.github.marschall.getrandom;

import static com.github.marschall.getrandom.AllZeros.allZeros;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import org.junit.jupiter.api.Test;

class GetrandomProviderTest {

  @Test
  void getrandom() throws GeneralSecurityException {
    SecureRandom secureRandom;

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM);
    verify(secureRandom, 16); // avoid emptying the entropy pool (/proc/sys/kernel/random/entropy_avail)

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM, GetrandomProvider.NAME);
    verify(secureRandom, 16); // avoid emptying the entropy pool (/proc/sys/kernel/random/entropy_avail)
  }

  @Test
  void geturandom() throws GeneralSecurityException {
    SecureRandom secureRandom;

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETURANDOM);
    verify(secureRandom, 128);

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETURANDOM, GetrandomProvider.NAME);
    verify(secureRandom, 128);
  }

  private static void verify(SecureRandom secureRandom, int poolSize) {
    assertNotNull(secureRandom);

    byte[] buffer = new byte[poolSize];
    assertThat(buffer, allZeros());

    secureRandom.nextBytes(buffer);
    assertThat(buffer, not(allZeros()));
  }

}

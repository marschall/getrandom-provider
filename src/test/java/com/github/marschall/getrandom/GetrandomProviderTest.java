package com.github.marschall.getrandom;

import static com.github.marschall.getrandom.GetrandomProviderTest.AllZeros.allZeros;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GetrandomProviderTest {

  @BeforeAll
  static void addProvider() {
    // in theory the ServiceLoader should find this but doesn't
    Security.addProvider(new GetrandomProvider());
  }

  @AfterAll
  static void installProvider() {
    Security.removeProvider(GetrandomProvider.NAME);
  }

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

  static final class AllZeros extends TypeSafeMatcher<byte[]> {

    private AllZeros() {
      super();
    }

    static Matcher<byte[]> allZeros() {
      return new AllZeros();
    }

    @Override
    public void describeTo(Description description) {
      description.appendText("is all 0x00");

    }

    @Override
    protected boolean matchesSafely(byte[] item) {
      for (byte b : item) {
        if (b != 0) {
          return false;
        }
      }
      return true;
    }

  }

}

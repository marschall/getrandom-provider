package com.github.marschall.getrandom;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GetrandomProviderTest {

  @BeforeAll
  static void addProvider() {
    Security.addProvider(new GetrandomProvider());
  }

  @AfterAll
  static void installProvider() {
    Security.removeProvider(GetrandomProvider.NAME);
  }

  @Test
  void getrandom() throws GeneralSecurityException {
    SecureRandom secureRandom;

    secureRandom = SecureRandom.getInstance("getrandom");
    verify(secureRandom);

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM, GetrandomProvider.NAME);
    verify(secureRandom);
  }

  @Test
  void geturandom() throws GeneralSecurityException {
    SecureRandom secureRandom;

    secureRandom = SecureRandom.getInstance("geturandom");
    verify(secureRandom);

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM, GetrandomProvider.NAME);
    verify(secureRandom);
  }

  private static void verify(SecureRandom secureRandom) {
    assertNotNull(secureRandom);

    byte[] buffer = new byte[128];
//    assertThat(buffer, allZeros());

    secureRandom.nextBytes(buffer);
//    assertThat(buffer, not(allZeros()));
  }

//  static final class AllZeros extends TypeSafeMatcher<byte[]> {
//
//    private AllZeros() {
//      super();
//    }
//
//    static Matcher<byte[]> allZeros() {
//      return new AllZeros();
//    }
//
//    @Override
//    public void describeTo(Description description) {
//      description.appendText("is all 0x00");
//
//    }
//
//    @Override
//    protected boolean matchesSafely(byte[] item) {
//      for (byte b : item) {
//        if (b != 0) {
//          return false;
//        }
//      }
//      return true;
//    }
//
//  }

}

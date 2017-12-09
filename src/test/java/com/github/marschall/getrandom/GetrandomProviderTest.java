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
    assertNotNull(secureRandom);

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM, GetrandomProvider.NAME);
    assertNotNull(secureRandom);
  }

  @Test
  void geturandom() throws GeneralSecurityException {
    SecureRandom secureRandom;

    secureRandom = SecureRandom.getInstance("geturandom");
    assertNotNull(secureRandom);

    secureRandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM, GetrandomProvider.NAME);
    assertNotNull(secureRandom);
  }

}

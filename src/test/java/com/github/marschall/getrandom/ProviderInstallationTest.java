package com.github.marschall.getrandom;

import java.security.Security;

import org.junit.jupiter.api.Test;

class ProviderInstallationTest {


  @Test
  void getProvider() {
    Security.getProvider(GetrandomProvider.NAME);
  }

}

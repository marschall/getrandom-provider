package com.github.marschall.getrandom;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

final class AllZeros extends TypeSafeMatcher<byte[]> {

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
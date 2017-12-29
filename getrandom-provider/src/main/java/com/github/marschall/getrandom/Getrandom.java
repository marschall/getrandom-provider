package com.github.marschall.getrandom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

final class Getrandom {

  static {
    String version = getVersion();
    String libraryName = "getrandom-provider-" + version;
    try {
      Runtime.getRuntime().loadLibrary(libraryName);
    } catch (UnsatisfiedLinkError e) {
      // the library is not in the library path
      // extract it from the JAR and load it from there
      String fileName = System.mapLibraryName(libraryName);
      Path extracted = extractLibrary(fileName);
      try {
        System.load(extracted.toAbsolutePath().toString());
      } finally {
        // under Linux can be deleted after loading
        try {
          Files.delete(extracted);
        } catch (IOException e1) {
          throw new AssertionError("could not delete temp file: " + fileName, e);
        }
      }
    }
  }

  private static String getVersion() {
    String fileName = "getrandom-provider.version";
    try (InputStream stream = Getrandom.class.getClassLoader().getResourceAsStream(fileName)) {
      if (stream == null) {
        throw new AssertionError("could not load resource: " + fileName);
      }
      ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
      byte[] buffer = new byte[16];
      int read = 0;
      do {
        if (read > 0) {
          bos.write(buffer, 0, read);
        }
        read = stream.read(buffer);
      } while (read != -1);
      return new String(bos.toByteArray(), StandardCharsets.US_ASCII);
    } catch (IOException e) {
      throw new AssertionError("could not load file: " + fileName, e);
    }
  }

  private static Path extractLibrary(String fileName) {
    Path tempFile;
    try {
      tempFile = Files.createTempFile("getrandom-provider-", ".so");
    } catch (IOException e) {
      throw new AssertionError("could not create temp file", e);
    }
    try (OutputStream output = Files.newOutputStream(tempFile);
         InputStream input = Getrandom.class.getClassLoader().getResourceAsStream(fileName)) {
      if (input == null) {
        throw new AssertionError("could not load resource: " + fileName);
      }
      byte[] buffer = new byte[8192];
      int read = 0;
      do {
        if (read > 0) {
          output.write(buffer, 0, read);
        }
        read = input.read(buffer);
      } while (read != -1);
    } catch (IOException e) {
      throw new AssertionError("could copy to temp file: " + fileName, e);
    }
    return tempFile;
  }

  private static final int EMALLOCNULL = 1;
  private static final int EFAULT = 14;
  private static final int EINTR = 4;
  private static final int EINVAL = 22;

  private Getrandom() {
    throw new AssertionError("not instantiable");
  }

  static void getrandom(byte[] bytes, boolean random) {
    Objects.requireNonNull(bytes);
    int exitCode = getrandom0(bytes, bytes.length, random);
    if (exitCode != 0) {
      switch (exitCode) {
        case EMALLOCNULL:
          throw new OutOfMemoryError("C heap exhaused malloc() returned NULL");
        case EFAULT:
          throw new IllegalStateException("The address referred to by buf is outside the accessible address space.");
        case EINTR:
          throw new IllegalStateException("The call was interrupted by a signal handler.");
        case EINVAL:
          throw new IllegalStateException("An invalid flag was specified in flags.");
        default:
          throw new IllegalStateException("Encountered unknown exit code: " + exitCode + ".");
      }
    }
  }


  // http://man7.org/linux/man-pages/man2/getrandom.2.html
  private static native int getrandom0(byte[] bytes, int length, boolean random);

}

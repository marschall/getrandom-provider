# getrandom() SecureRandomSPI

A `SecureRandomSPI` that makes [getrandom()](http://man7.org/linux/man-pages/man2/getrandom.2.html) system call available to `SecureRandom`.


* uses syscall, does not depend on glibc wrapper
* tries to use stack allocation rather than allocation on the C heap
* is marked as thread safe so concurrent access through `SecureRandom` will not synchronize in Java 9 and later
* unlike the `NativePRNG` variants does not use a file handle
* unlike the `NativePRNG` variants does not have a global lock
* unlike the `NativePRNG` variants does not additionally mix with SHA-1
* unlike the `NativePRNG` variants blocks until the urandom source has been initialized


## Usage

A nonblocking (/dev/urandom) instance of the provider can be acquired using

```java
SecureRandom.getInstance(GetrandomProvider.GETURANDOM); // "geturandom"
```

Alternatively a blocking (/dev/random) instance of the provider can be acquired using

```java
SecureRandom.getInstance(GetrandomProvider.GETRANDOM); // "getrandom"
```

The /dev/urandom variant in the preferred one, the /dev/random variant is only added for completeness, see [Myths about /dev/urandom](https://www.2uo.de/myths-about-urandom/).

## Configuration

The provider can be configured in two different ways

1. Programmatic configuration
1. Static configuration

For best startup performance it is recommended to extract the .so from the JAR and add it to a folder present in the `LD_LIBRARY_PATH` environment variable or the `java.library.path` system property. Otherwise this library will extract the .so to a temporary folder the first time it is called.

### Programmatic configuration

The provider can be registered programmatically using

```java
Security.addProvider(new GetrandomProvider());
```

### Static configuration

The provider can be configured statically in the `java.security` file by adding the provider at the end


```
security.provider.12=...
security.provider.13=...
security.provider.14=getrandom

```

This can be done [per JVM installation](https://docs.oracle.com/javase/9/security/howtoimplaprovider.htm#GUID-831AA25F-F702-442D-A2E4-8DA6DEA16F33) or [per JVM Instance](https://dzone.com/articles/how-override-java-security).

The provider uses the ServiceLoader mechanism therefore using the `getrandom` string is enough, there is no need to use the fully qualified class name.

Note that for this to work the provider JAR needs to be in the class path or module path.

### Misc

* marked as ThreadSafe in Java 9
* supports the ServiceLoader mechanism


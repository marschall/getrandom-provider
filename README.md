# getrandom() SecureRandomSPI [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/getrandom-provider/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/getrandom-provider)  [![Javadocs](https://www.javadoc.io/badge/com.github.marschall/getrandom-provider.svg)](https://www.javadoc.io/doc/com.github.marschall/getrandom-provider)

A `SecureRandomSPI` that makes [getrandom()](http://man7.org/linux/man-pages/man2/getrandom.2.html) system call available to `SecureRandom`.

* uses syscall, does not depend on glibc wrapper
* tries to use stack allocation rather than allocation on the C heap
* is marked as thread safe so concurrent access through `SecureRandom` will not synchronize in Java 9 and later, however there seems to be a kernel lock on /dev/urandom so you will likely not get additional parallelism
* unlike the `NativePRNG` variants
  * does not use a file handle
  * does not have a global lock, but see comments on the kernel lock above
  * does not additionally mix with `SHA1PRNG`
  * blocks until the urandom source has been initialized
  * zeros out native memory
* supports the ServiceLoader mechanism
* is a Java 9 module but works on Java 8
* no dependencies outside the `java.base` module

## Usage

A nonblocking (/dev/urandom) instance of the provider can be acquired using

```java
SecureRandom.getInstance("geturandom"); // GetrandomProvider.GETURANDOM
```

Alternatively a blocking (/dev/random) instance of the provider can be acquired using

```java
SecureRandom.getInstance("getrandom"); // GetrandomProvider.GETRANDOM
```

The /dev/urandom variant is the preferred one, the /dev/random variant is only added for completeness, see [Myths about /dev/urandom](https://www.2uo.de/myths-about-urandom/).

## Configuration

The provider can be configured in two different ways

1. programmatic configuration
1. static configuration

For best startup performance it is recommended to extract the .so from the JAR and add it to a folder present in the `LD_LIBRARY_PATH` environment variable or the `java.library.path` system property. Otherwise this library will extract the .so to a temporary folder the first time it is called.

### Programmatic Configuration

The provider can be registered programmatically using

```java
Security.addProvider(new GetrandomProvider());
```

### Static Configuration Java 8

The provider can be configured statically in the `java.security` file by adding the provider at the end

```
security.provider.N=com.github.marschall.getrandom.GetrandomProvider
```

`N` should be the value of the last provider incremented by 1. For Oracle/OpenJDK 8 on Linux `N` should likely be 10.

This can be done [per JVM installation](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/HowToImplAProvider.html#Configuring) or [per JVM Instance](https://dzone.com/articles/how-override-java-security).

Note that for this to work the provider JAR needs to be in the class path or extension folder.

### Static Configuration Java 9+

The provider can be configured statically in the `java.security` file by adding the provider at the end

```
security.provider.N=getrandom
```

`N` should be the value of the last provider incremented by 1. For Oracle/OpenJDK 9 on Linux `N` should likely be 13.

This can be done [per JVM installation](https://docs.oracle.com/javase/9/security/howtoimplaprovider.htm#GUID-831AA25F-F702-442D-A2E4-8DA6DEA16F33) or [per JVM Instance](https://dzone.com/articles/how-override-java-security).

The provider uses the ServiceLoader mechanism therefore using the `getrandom` string is enough, there is no need to use the fully qualified class name.

Note that for this to work the provider JAR needs to be in the class path or module path.

### Usage for Tomcat Session Ids

This security provider can be used for session id generation in Tomcat. In order for that several things need to be configured:

1. the JAR needs to be added to the class path
1. the .so should be added to the Java library path (`java.library.path`)
1. the provider needs to be installed into the JVM via `java.security.properties`
1. Tomcat needs to be configured to use the algorithm

Points 1, 2 and 3 can be configured in `CATALINA_BASE/bin/setenv.sh`

```sh
#!/bin/sh

CLASSPATH="/path/to/getrandom-provider-0.1.1.jar"
CATALINA_OPTS="$CATALINA_OPTS -Djava.library.path=/path/to/folder/with/so -Djava.security.properties=/path/to/jvm.java.security"

export CLASSPATH
export CATALINA_OPTS
```

Point can be configured on [the Manager Component](https://tomcat.apache.org/tomcat-8.5-doc/config/manager.html) in `conf/context.xml` by setting `secureRandomAlgorithm` to `geturandom`

```xml
<Manager secureRandomAlgorithm="geturandom">
</Manager>
```

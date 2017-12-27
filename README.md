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
security.provider.N=getrandom
```

`N` should be the value of the last provider incremented by 1.

This can be done [per JVM installation](https://docs.oracle.com/javase/9/security/howtoimplaprovider.htm#GUID-831AA25F-F702-442D-A2E4-8DA6DEA16F33) or [per JVM Instance](https://dzone.com/articles/how-override-java-security).

The provider uses the ServiceLoader mechanism therefore using the `getrandom` string is enough, there is no need to use the fully qualified class name.

Note that for this to work the provider JAR needs to be in the class path or module path.

### Misc

* marked as ThreadSafe in Java 9
* supports the ServiceLoader mechanism

### Usage for Tomcat Session Ids

This security provider can be used for session id generation in Tomcat. In order for that several things need to be configured:

1. the JAR needs to be added to the class path
1. the .so should be added to the Java library path (`java.library.path`)
1. the provider needs to be installed into the JVM via `java.security.properties`
1. Tomcat needs to be configured to use the algorithm

Points 1, 2 and 3 can be configured in `CATALINA_BASE/bin/setenv.sh`

```
CLASSPATH=/path/to/getrandom-provider-0.1.0.jar
CATALINA_OPTS=-Djava.library.path=/path/to/folder/with/so -Djava.security.properties=/path/to/jvm.java.security
```

Point can be configured on [the Manager Component](https://tomcat.apache.org/tomcat-8.5-doc/config/manager.html) in `conf/context.xml` by setting `secureRandomAlgorithm` to `geturandom`

```xml
<Manager secureRandomAlgorithm="geturandom">
</Manager>
```

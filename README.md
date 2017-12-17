getrandom() SecureRandomSPI
===========================

A `SecureRandomSPI` that makes [getrandom()](http://man7.org/linux/man-pages/man2/getrandom.2.html) system call available to `SecureRandom`.


* uses syscall, does not depend on glibc wrapper
* tries to use stack allocation rather than allocation on the C heap
* is marked as thread safe so concurrent access through `SecureRandom` will not synchronize in Java 9 and later
* unlike the `NativePRNG` variants does not use a file handle
* unlike the `NativePRNG` variants does not have a global lock
* unlike the `NativePRNG` variants does not additionally mix with SHA-1


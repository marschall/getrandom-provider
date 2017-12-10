getrandom() SecureRandomSPI
===========================

A `SecureRandomSPI` that makes [getrandom()](http://man7.org/linux/man-pages/man2/getrandom.2.html) system call available to `SecureRandom`.


* uses syscall, does not depend on glibc wrapper
* tries to use stack allocation rather than allocation on the C heap


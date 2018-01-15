#!/bin/bash

set -e

VERSION=0.1.1-SNAPSHOT
export VERSION

extract_so() {
  if [ ! -f libgetrandom-provider-${VERSION}.so ]; then
    unzip -p target/getrandom-provider-benchmarks-${VERSION}.jar libgetrandom-provider-${VERSION}.so > target/libgetrandom-provider-${VERSION}.so
  fi
}

reaname_output() {
  mv threads-1.txt threads-1-$1.txt
  mv threads-2.txt threads-2-$1.txt
  mv threads-4.txt threads-4-$1.txt
  mv threads-8.txt threads-8-$1.txt
}

main() {
  extract_so

  export JAVA_HOME=/home/marschall/bin/java/jdk-8
  ./src/main/bash/run-benchmarks-8.sh
  reaname_output java-8

  export JAVA_HOME=/home/marschall/bin/java/graalvm-8
  ./src/main/bash/run-benchmarks-8.sh
  reaname_output graal-8

  export JAVA_HOME=/home/marschall/bin/java/jdk-9
  ./src/main/bash/run-benchmarks-9.sh
  reaname_output java-9

  export JAVA_HOME=/home/marschall/bin/java/jdk-9
  ./src/main/bash/run-benchmarks-graal9.sh
  reaname_output graal-9

#  export JAVA_HOME=/home/marschall/bin/java/openj9-9
#  ./src/main/bash/run-benchmarks-j9.sh
#  reaname_output ibm-9
}

main

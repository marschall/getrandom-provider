#!/bin/bash

set -e

reaname_output() {
  mv threads-1.txt threads-1-$1.txt
  mv threads-2.txt threads-2-$1.txt
  mv threads-4.txt threads-4-$1.txt
  mv threads-8.txt threads-8-$1.txt
}

main() {
  export JAVA_HOME=/home/marschall/bin/java/jdk-8
  ./src/main/bash/run-benchmarks.sh
  reaname_output java-8

  export JAVA_HOME=/home/marschall/bin/java/graalvm-8
  ./src/main/bash/run-benchmarks.sh
  reaname_output graal-8

  export JAVA_HOME=/home/marschall/bin/java/jdk-9
  ./src/main/bash/run-benchmarks.sh
  reaname_output java-9
}

main

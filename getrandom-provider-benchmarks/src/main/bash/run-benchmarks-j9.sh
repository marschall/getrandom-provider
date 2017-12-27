#!/bin/bash

set -e

run_benchark() {
   $JAVA_HOME/bin/java \
     -Xgcpolicy:optthruput \
     -Djava.security.properties=src/main/resources/jvm.java9.security \
     -Xmx32g -Xms32g \
     -jar target/getrandom-provider-benchmarks-${VERSION}.jar \
       $1 threads-$1.txt
}

main() {
  run_benchark 1
  run_benchark 2
  run_benchark 4
  run_benchark 8
}

main


#!/bin/bash

set -e

run_benchark() {
   $JAVA_HOME/bin/java \
     -XX:+UseParallelGC \
     -Xmx32g -Xms32g \
     -Djava.security.properties=src/main/resources/jvm.java9.security \
     -jar target/getrandom-provider-benchmarks-0.1.0-SNAPSHOT.jar \
       $1 threads-$1.txt
}

main() {
  run_benchark 1
  run_benchark 2
  run_benchark 4
  run_benchark 8
}

main


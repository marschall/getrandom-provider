#!/bin/bash

set -e

run_benchark() {
   $JAVA_HOME/bin/java \
     -XX:+UseParallelGC \
     -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler \
     -Xmx1g -Xms1g \
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


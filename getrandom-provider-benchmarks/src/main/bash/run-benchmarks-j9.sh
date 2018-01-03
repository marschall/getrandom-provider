#!/bin/bash

set -e

run_benchark() {
   $JAVA_HOME/bin/java \
     -Xgcpolicy:optthruput \
     -Xmx32g -Xms32g \
     -Djava.security.properties=src/main/resources/jvm.java9.security \
     -Djava.library.path=/home/marschall/git/getrandom-provider/getrandom-provider-benchmarks/target \
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


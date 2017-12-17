#!/bin/bash

$JAVA_HOME/bin/java \
 -XX:+UseParallelGC \
 -Xmx1g -Xms1g \
 -jar target/getrandom-provider-benchmarks-0.1.0-SNAPSHOT.jar \
 1 threads-1.txt

# $JAVA_HOME/bin/java \
#  -XX:+UseParallelGC \
#  -Xmx1g -Xms1g \
#  -jar target/getrandom-provider-benchmarks-1.0.0-SNAPSHOT.jar \
#  2 threads-2.txt
# 
# $JAVA_HOME/bin/java \
#  -XX:+UseParallelGC \
#  -Xmx1g -Xms1g \
#  -jar target/getrandom-provider-benchmarks-1.0.0-SNAPSHOT.jar \
#  4 threads-4.txt
# 
# $JAVA_HOME/bin/java \
#  -XX:+UseParallelGC \
#  -Xmx1g -Xms1g \
#  -jar target/getrandom-provider-benchmarks-1.0.0-SNAPSHOT.jar \
#  8 threads-8.txt

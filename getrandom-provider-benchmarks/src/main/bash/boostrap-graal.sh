#!/bin/bash

set -e

$JAVA_HOME/bin/java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler -XX:+BootstrapJVMCI -version

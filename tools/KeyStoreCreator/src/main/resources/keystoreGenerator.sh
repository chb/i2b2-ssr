#!/bin/bash

CMD=""

if [ -z "$JAVA_HOME" ]; then
  CMD=java
else
  CMD=$JAVA_HOME/bin/java
fi

CLASSPATH=.:lib/*

$CMD -classpath $CLASSPATH edu.chip.carranet.keystore.automation.KeystoreGenerator $@

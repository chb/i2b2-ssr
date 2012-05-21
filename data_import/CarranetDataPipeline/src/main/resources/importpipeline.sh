#!/bin/bash

CMD=""

if [ -z "$JAVA_HOME" ]; then
  CMD=java
else
  CMD=$JAVA_HOME/bin/java
fi

CLASSPATH=.:lib/*

$CMD -classpath $CLASSPATH -server -Dsun.net.inetaddr.ttl=500 -Djava.security.egd=file:/dev/../dev/urandom -XX:+HeapDumpOnOutOfMemoryError edu.chip.carranet.carradatapipeline.ImportPipeline
ruby import_exit_notification.rb
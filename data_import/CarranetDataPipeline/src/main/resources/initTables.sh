#!/bin/bash

CMD=""

if [ -z "$JAVA_HOME" ]; then
  CMD=java
else
  CMD=$JAVA_HOME/bin/java
fi

CLASSPATH=.:lib/*

$CMD -classpath $CLASSPATH -server -Dsun.net.inetaddr.ttl=500 edu.chip.carranet.carradatapipeline.InitPipelineStoreTables createTable.properties

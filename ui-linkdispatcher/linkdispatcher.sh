#!/bin/sh

JAVA_HOME=/home/mira/apps/jdk1.8.0
DISPATCHER_HOME=./target

$JAVA_HOME/bin/java -jar $DISPATCHER_HOME/ui-linkdispatcher-0.0.1-SNAPSHOT.jar $@

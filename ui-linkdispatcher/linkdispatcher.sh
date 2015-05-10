#!/bin/sh

#JAVA_HOME=/home/mira/apps/jdk1.8.0

JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/
DISPATCHER_HOME=/home/mira/git/ui-linkdispatcher/ui-linkdispatcher/target

$JAVA_HOME/bin/java -jar $DISPATCHER_HOME/ui-linkdispatcher-0.0.1-SNAPSHOT.jar $@

#!/bin/bash

nohup /usr/bin/telegraf > /var/log/telegraf/telegraf.log &
exec java -jar -Dspring.profiles.active=$SPRING_PROFILE $TIMETRACK_BACKEND_HOME/$TIMETRACK_SERVER_JAR
FROM java:8-jdk

EXPOSE 8080

ENV TIMETRACK_HOME /opt/backend
ENV SPRING_PROFILE prod

CMD /usr/lib/jvm/java-8-openjdk-amd64/bin/java -jar $TIMETRACK_HOME/backend.jar -Dspring.profiles.active=$SPRING_PROFILE
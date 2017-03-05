FROM frolvlad/alpine-oraclejdk8:slim

ADD ./target/admin-ui.jar admin-ui.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /admin-ui.jar" ]
FROM java:8-jdk

EXPOSE 8080

ENV SPRING_PROFILE prod
ENV TIMETRACK_BACKEND_HOME /opt/timetrack
ENV TIMETRACK_SERVER_JAR backend-server.jar
ENV TIMETRACK_TOOLS_JAR backend-tools.jar

RUN cd /tmp && \
    wget https://dl.influxdata.com/telegraf/releases/telegraf_1.2.1_amd64.deb -nv && \
    dpkg -i telegraf_1.2.1_amd64.deb
COPY ./config/telegraf.conf /etc/telegraf/telegraf.conf

COPY ./backend-server/target/$TIMETRACK_SERVER_JAR $TIMETRACK_BACKEND_HOME/$TIMETRACK_SERVER_JAR
COPY ./backend-tools/target/$TIMETRACK_TOOLS_JAR $TIMETRACK_BACKEND_HOME/$TIMETRACK_TOOLS_JAR

ADD config/entrypoint.sh /tmp/entrypoint.sh
RUN chmod +x /tmp/entrypoint.sh

CMD ["/tmp/entrypoint.sh"]
FROM grafana/grafana:latest

EXPOSE 3000:3000

RUN apt-get update && \
    apt-get -y install curl && \
    apt-get clean && \
    mkdir -p /etc/grafana/scripts && mkdir /etc/grafana/scripts/dashboards && \
    mkdir -p /var/log/telegraf && touch /var/log/telegraf/telegraf.log

COPY ./config/datasource.json /etc/grafana/scripts/datasource.json
COPY ./config/dashboards/system.json /etc/grafana/scripts/dashboards/
COPY ./config/dashboards/performance.json /etc/grafana/scripts/dashboards/
COPY ./config/dashboards/users.json /etc/grafana/scripts/dashboards/
COPY ./config/dashboards/docker.json /etc/grafana/scripts/dashboards/

COPY ./config/grafana-conf.cmd /etc/grafana/scripts/grafana-conf.cmd
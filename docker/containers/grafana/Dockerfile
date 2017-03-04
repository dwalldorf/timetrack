FROM grafana/grafana

RUN mkdir -p /etc/grafana/scripts
COPY ./config/datasource.json /etc/grafana/scripts/datasource.json
COPY ./config/grafana-conf.cmd /etc/grafana/scripts/grafana-conf.cmd
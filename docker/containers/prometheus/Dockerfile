FROM prom/prometheus:latest

EXPOSE 9090

COPY ./config/prometheus.yml /etc/prometheus/prometheus.yml
WORKDIR    /prometheus
ENTRYPOINT [ "/bin/prometheus" ]
CMD        [ "-config.file=/etc/prometheus/prometheus.yml", \
             "-storage.local.path=/prometheus" \
           ]
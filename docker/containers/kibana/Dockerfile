FROM kibana:5

RUN apt-get update && \
    apt-get install -y \
    netcat \
    bzip2 \
    curl && \
    apt-get clean

COPY config/kibana.yml /etc/kibana/kibana.yml
COPY entrypoint.sh /tmp/entrypoint.sh
RUN chmod +x /tmp/entrypoint.sh

CMD ["/tmp/entrypoint.sh"]
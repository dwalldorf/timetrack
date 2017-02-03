FROM kibana:5

RUN apt-get update && apt-get install -y netcat bzip2 curl

COPY entrypoint.sh /tmp/entrypoint.sh
COPY data /tmp/data
RUN chmod +x /tmp/entrypoint.sh

CMD ["/tmp/entrypoint.sh"]
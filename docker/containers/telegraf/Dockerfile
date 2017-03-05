FROM telegraf:latest

COPY ./config/telegraf.conf /etc/telegraf/telegraf.conf

COPY entrypoint.sh /tmp/entrypoint.sh
RUN chmod +x /tmp/entrypoint.sh

CMD ["/tmp/entrypoint.sh"]
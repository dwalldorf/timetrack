#!/usr/bin/env bash

echo "Stalling for influxdb"
while true; do
    nc -q 1 influxdb 8086 2>/dev/null && break
done

echo "Starting telegraf"
exec telegraf start
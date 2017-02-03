#!/usr/bin/env bash

# Wait for the Elasticsearch container to be ready before starting Kibana.
echo "Stalling for Elasticsearch"
while true; do
    nc -q 1 elasticsearch 9200 2>/dev/null && break
done

curl -XPUT elasticsearch:9200/.kibana/search/test -d data/search/test.json

echo "Starting Kibana"
exec kibana
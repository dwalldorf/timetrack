curl -v POST http://localhost:3000/api/datasources -d @/etc/grafana/scripts/influxdb.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
curl -v POST http://localhost:3000/api/datasources -d @/etc/grafana/scripts/prometheus.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='

curl -v POST http://localhost:3000/api/dashboards/db -d @/etc/grafana/scripts/dashboards/users.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
curl -v POST http://localhost:3000/api/dashboards/db -d @/etc/grafana/scripts/dashboards/performance.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
curl -v POST http://localhost:3000/api/dashboards/db -d @/etc/grafana/scripts/dashboards/system.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
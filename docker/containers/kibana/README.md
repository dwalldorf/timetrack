# timetrack kibana

[localhost:5601](http://localhost:5601)

## Run
Run with `make run-env` or `docker-compose up -d kibana` from project root.

## Configure
To use kibana, you need the container running and at start the backend application once,
so there are logs to create the index pattern. In doubt, `make up` will get you there.

Open kibana in your browser and configure the index pattern `timetrack-*`.
Now you can go to Management -> Saved Objects and import [savedObjects.json](data/savedObjects.json).

---
[to docker](/docker)

---
[to project root](https://github.com/dwalldorf/timetrack)
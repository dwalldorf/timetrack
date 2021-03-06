# Timetrack

## Components
- [backend](backend)
- [frontend](frontend)
- [docker](docker)
  - [elasticsearch](docker/containers/elasticsearch)
  - [kibana](docker/containers/kibana)
  - [mongo](docker/containers/mongo)
  - [redis](docker/containers/redis)


## Prerequisites
Running this project requires 

* [jdk8 or higher](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [maven](https://maven.apache.org/)
* [docker](https://www.docker.com/products/overview)
* [node/npm](https://nodejs.org/)


## Run it
In the root of the project, type `make build && make up` to build the project and run all components in docker containers.

For development, you may want to `make run-env`, which will start required components such as mongo and a container to 
monitor the frontend and rebuild it on the fly, but shutdown the backend so you can start it on your machine. 
Run the backend with the `dev` profile in this case.

Check out the [./Makefile](Makefile) for more useful commands.


# License
[MIT](LICENSE)
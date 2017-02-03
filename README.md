# Timetrack

## Components
- [./backend](backend)
- [./frontend](frontend)
- [./docker](docker)
  - [./docker/containers/elasticsearch](elasticsearch)
  - [./docker/containers/kibana](kibana)
  - [./docker/containers/mong](mongo)

## Run it
In the root of the project, type `make build && make up` to build the project all components in docker containers.

For development, you may want to `make run-env`, which will start required components such as mongo and a container to 
monitor the frontend and rebuild it on the fly, but shutdown the backend so you can start it on your machine. 
Run the backend with the `dev` profile in this case.

Check out the [./Makefile](Makefile) for more useful commands.

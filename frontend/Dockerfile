FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y curl build-essential python && \
    (curl -sL https://deb.nodesource.com/setup_6.x | bash -E -) && \
    apt-get update && \
    apt-get install -y nodejs

RUN mkdir -p /html && \
    npm install -g \
        lite-server \
        node-sass \
        nodemon

WORKDIR /html

EXPOSE 3000 3001
CMD npm install && npm start
FROM ubuntu:latest

EXPOSE 3000 3001

RUN apt-get update && \
    apt-get install -y curl build-essential python && \
    (curl -sL https://deb.nodesource.com/setup_6.x | bash -E -) && \
    apt-get update && \
    apt-get install -y nodejs && \

    mkdir -p /html && \
    npm install -g \
        lite-server \
        node-sass \
        nodemon && \

    apt-get clean

WORKDIR /html

CMD npm install && npm start
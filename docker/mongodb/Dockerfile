FROM debian:wheezy
MAINTAINER Pierre Chaussalet <pchaussalet@gmail.com>

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y mongodb mongodb-server

RUN sed -i 's/^bind/#bind/' /etc/mongodb.conf

EXPOSE 27027 28017

ENTRYPOINT ["/usr/bin/mongod", "-f", "/etc/mongodb.conf"]


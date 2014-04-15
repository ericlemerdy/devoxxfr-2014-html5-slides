FROM debian:jessie
MAINTAINER Pierre Chaussalet <pchaussalet@gmail.com>

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y openjdk-7-jre-headless

ONBUILD ADD mepc-server.jar /opt/mepc-server.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/mepc-server.jar"]

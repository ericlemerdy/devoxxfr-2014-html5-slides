FROM debian:jessie
MAINTAINER Pierre Chaussalet <pchaussalet@gmail.com>

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y nginx openssh-sftp-server
RUN mkdir -p /var/run/sshd && \
    mkdir -p /var/www && \
    rm -rf /usr/share/nginx/html && \
    ln -s /var/www /usr/share/nginx/html
RUN echo root:mepcRul3Z |chpasswd
ADD start.sh /root/start.sh

EXPOSE 80
EXPOSE 22

ENTRYPOINT "/root/start.sh"

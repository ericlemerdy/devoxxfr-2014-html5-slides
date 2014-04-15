#!/bin/sh
echo root:mepcRul3Z |chpasswd
/usr/sbin/nginx
/usr/sbin/sshd -E /var/log/sshd.log
tail -f /var/log/sshd.log

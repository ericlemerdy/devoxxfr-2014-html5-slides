#!/bin/sh
docker run -d -t -p 22000:22 -p 80:80 --name front mepc/front:1

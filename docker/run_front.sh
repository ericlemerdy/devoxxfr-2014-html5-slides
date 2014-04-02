#!/bin/sh
docker run -d -t -p 2222:22 -p 80:80 --name front mepc/front

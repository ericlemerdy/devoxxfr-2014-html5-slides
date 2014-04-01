#!/bin/sh
docker run -d -t -p 2222:22 -p 8080:80 --name blue mepc/front
docker run -d -t -p 2200:22 -p 8081:80 --name green mepc/front

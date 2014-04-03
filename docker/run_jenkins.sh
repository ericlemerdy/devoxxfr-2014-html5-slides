#!/bin/sh
docker run -d -t -p 2200:22 -p 8080:80 --name jenkins mepc/jenkins
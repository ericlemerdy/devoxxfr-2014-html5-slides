#!/bin/sh
cd front
docker build --rm -t mepc/front:1 .
cd ..

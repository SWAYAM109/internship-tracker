#!/bin/bash
set -e
cd "$(dirname "$0")/.."
docker build -t internship-tracker:1.0 .
docker run -d -p 9090:8080 --name tracker-test internship-tracker:1.0
sleep 15
curl localhost:9090/applications
docker rm -f tracker-test
docker images | grep internship-tracker

#!/bin/bash
# Build, run locally and test every endpoint (Component 1, steps 1 and 3)
set -e
cd "$(dirname "$0")/.."
mvn -B clean package
java -jar target/tracker.jar --server.port=8081 &
PID=$!
sleep 15
curl -s -X POST localhost:8081/applications -H "Content-Type: application/json" \
  -d '{"company":"Microsoft","role":"Software Engineer Intern"}'; echo
curl -s -X POST localhost:8081/applications -H "Content-Type: application/json" \
  -d '{"company":"Google","role":"STEP Intern"}'; echo
curl -s localhost:8081/applications; echo
curl -s localhost:8081/applications/1; echo
curl -s -X PUT localhost:8081/applications/1/status -H "Content-Type: application/json" \
  -d '{"status":"Interview"}'; echo
curl -s localhost:8081/actuator/health; echo
kill $PID

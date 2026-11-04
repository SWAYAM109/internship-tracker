#!/bin/bash
set -e
echo "=== BEFORE ==="; kubectl get pods
kubectl scale deployment tracker-deployment --replicas=5
kubectl rollout status deployment/tracker-deployment --timeout=180s
echo "=== AFTER (5) ==="; kubectl get pods
kubectl scale deployment tracker-deployment --replicas=3
sleep 10
echo "=== BACK TO 3 ==="; kubectl get pods

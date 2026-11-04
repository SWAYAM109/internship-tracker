#!/bin/bash
# Deploys a broken image, then rolls back
echo "=== Deploy broken version ==="
kubectl set image deployment/tracker-deployment tracker=internship-tracker:broken
sleep 15
kubectl get pods            # new pods show ErrImageNeverPull; old pods keep serving
echo "=== Rollback ==="
kubectl rollout undo deployment/tracker-deployment
kubectl rollout status deployment/tracker-deployment --timeout=180s
kubectl get pods
kubectl rollout history deployment/tracker-deployment

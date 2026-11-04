#!/bin/bash
set -e
cd "$(dirname "$0")/.."
minikube start --driver=docker
minikube image load internship-tracker:1.0
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl rollout status deployment/tracker-deployment --timeout=180s
kubectl get pods
kubectl get svc
echo "Access: kubectl port-forward svc/tracker-service 8085:80   then   curl localhost:8085/applications"

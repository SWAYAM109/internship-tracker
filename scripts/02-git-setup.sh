#!/bin/bash
# Creates a Git history with 3 feature branches and 3 merges.
# Usage: ./scripts/02-git-setup.sh https://github.com/<you>/internship-tracker.git
set -e
cd "$(dirname "$0")/.."
REPO_URL="$1"
git init -b main
git add .gitignore README.md
git commit -m "Initial commit: README and gitignore"

git checkout -b feature/tracker-api
git add pom.xml src Dockerfile .dockerignore
git commit -m "Add tracker REST API, unit tests and Dockerfile"
git checkout main
git merge --no-ff feature/tracker-api -m "Merge feature/tracker-api into main"

git checkout -b feature/jenkins-ci
git add Jenkinsfile .github scripts
git commit -m "Add Jenkinsfile (test, build, versioning) and GitHub Actions"
git checkout main
git merge --no-ff feature/jenkins-ci -m "Merge feature/jenkins-ci into main"

git checkout -b feature/ansible-k8s
git add ansible k8s repo_link.txt
git commit -m "Add Ansible role and Kubernetes manifests"
git checkout main
git merge --no-ff feature/ansible-k8s -m "Merge feature/ansible-k8s into main"

git log --oneline --graph --all
if [ -n "$REPO_URL" ]; then
  git remote add origin "$REPO_URL"
  git push -u origin main
  git push origin feature/tracker-api feature/jenkins-ci feature/ansible-k8s
fi

#!/bin/bash
set -e
cd "$(dirname "$0")/.."
mvn -B clean package -DskipTests
cd ansible
ansible-playbook -i inventory playbook.yml --ask-become-pass
echo "--- second run (should show changed=0 for most tasks) ---"
ansible-playbook -i inventory playbook.yml --ask-become-pass
curl localhost:8081/applications
systemctl status tracker --no-pager

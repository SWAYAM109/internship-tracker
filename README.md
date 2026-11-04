# Internship Tracker 🚀

**A Java Spring Boot REST API application demonstrating an end-to-end
DevOps workflow using Jenkins, Docker, Ansible, and Kubernetes.**

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring
Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.10.0-blue)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerization-blue)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-Orchestration-326CE5)](https://kubernetes.io/)

## 📌 Project Overview

Internship Tracker is a REST API application developed using Java and
Spring Boot to manage internship applications.

The project demonstrates how a Java application can be built, tested,
packaged, containerized, and deployed using modern DevOps tools.

The project integrates:

-   **Git and GitHub** -- Version control and source code management.
-   **Jenkins** -- Continuous Integration and automated build pipeline.
-   **Maven** -- Dependency management and application packaging.
-   **Docker** -- Application containerization.
-   **Ansible** -- Configuration management and automated deployment.
-   **Kubernetes** -- Container orchestration and application scaling.

## 🎯 Project Objectives

-   Develop REST APIs for managing internship applications.
-   Automate compilation and testing using Jenkins.
-   Package the application as an executable JAR.
-   Containerize the application using Docker.
-   Automate server configuration and deployment using Ansible.
-   Deploy and manage multiple application replicas using Kubernetes.
-   Verify application availability through health checks.

## 🛠️ Technology Stack

  Category                   Technology
  -------------------------- ------------------------
  Programming Language       Java 17
  Backend Framework          Spring Boot 3.2.5
  Build Tool                 Apache Maven 3.10.0
  Testing Framework          JUnit 5
  Version Control            Git and GitHub
  CI Pipeline                Jenkins
  Containerization           Docker
  Configuration Management   Ansible
  Container Orchestration    Kubernetes
  Operating Environment      Windows 11, WSL Ubuntu

## 🏗️ Project Architecture

``` text
                  Developer
                      |
                      v
                 Git / GitHub
                      |
                      v
                   Jenkins
                      |
              -----------------
              |       |       |
           Compile   Test   Package
              |       |       |
              -----------------
                      |
                      v
                Executable JAR
                      |
                      v
                 Docker Image
                      |
                      v
                 Kubernetes
                      |
             -------------------
             |        |        |
            Pod 1    Pod 2    Pod 3
             |        |        |
             -------------------
                      |
                      v
              Kubernetes Service
                      |
                      v
                   REST APIs
```

**Ansible deployment:** Ansible separately configures the deployment
environment, installs Java, copies the application JAR, and manages the
application through a systemd service.

## 📂 Project Structure

``` text
internship-tracker/
│
├── .github/
├── ansible/
│   ├── ansible.cfg
│   ├── inventory
│   ├── playbook.yml
│   └── roles/
│       └── tracker/
│           ├── defaults/
│           ├── handlers/
│           ├── tasks/
│           ├── templates/
│           └── vars/
│
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
│
├── scripts/
├── src/
│   ├── main/
│   └── test/
│
├── target/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── README.md
```

## ⚙️ Application Features

-   Create internship applications.
-   Retrieve all internship applications.
-   Retrieve an individual application using its ID.
-   Maintain application status.
-   Monitor application health using Spring Boot Actuator.

### Supported Application Statuses

-   Applied
-   Interview
-   Rejected
-   Offer

## 🔗 REST API Documentation

Base URL:

``` text
http://localhost:8080
```

  HTTP Method   Endpoint               Description
  ------------- ---------------------- ----------------------------
  GET           `/applications`        Retrieve all applications
  POST          `/applications`        Create a new application
  GET           `/applications/{id}`   Retrieve application by ID
  GET           `/actuator/health`     Check application health

### 1. Create an Application

**POST** `/applications`

Request body:

``` json
{
  "company": "Google",
  "role": "Software Intern",
  "status": "Applied"
}
```

Example response:

``` json
{
  "id": 1,
  "company": "Google",
  "role": "Software Intern",
  "status": "Applied"
}
```

### 2. Retrieve All Applications

**GET** `/applications`

Example response:

``` json
[
  {
    "id": 1,
    "company": "Google",
    "role": "Software Intern",
    "status": "Applied"
  }
]
```

### 3. Retrieve an Application by ID

**GET** `/applications/1`

Returns the application associated with ID 1.

## 🔄 Jenkins Continuous Integration

Jenkins automates the application build and testing process.

### Pipeline Stages

1.  **Checkout:** Retrieves source code from the GitHub repository.
2.  **Compile:** Compiles the Java application using Maven.
3.  **Test:** Executes automated tests using JUnit 5.
4.  **Package:** Generates the executable JAR file.
5.  **Archive:** Archives `target/tracker.jar` as a Jenkins build
    artifact.

### Jenkins Configuration

-   JDK: 17
-   Maven: 3.10.0
-   Repository: GitHub
-   Branch: `main`

The Jenkins pipeline was successfully executed, with all stages
completing successfully.

## 🐳 Docker Containerization

Docker packages the application and its runtime environment into a
container.

### Build Docker Image

``` bash
docker build -t internship-tracker:1.0 .
```

### Run Docker Container

``` bash
docker run -d --name internship-tracker -p 8081:8080 internship-tracker:1.0
```

### Verify Running Containers

``` bash
docker ps
```

### Test Application

``` bash
curl http://localhost:8081/applications
```

The application runs on port 8080 inside the container and is exposed
through port 8081 on the host.

## ⚙️ Ansible Configuration Management

Ansible automates application deployment and server configuration.

### Tasks Performed

1.  Install Java 17 runtime.
2.  Create the application directory.
3.  Copy the executable JAR to the deployment server.
4.  Configure a systemd service.
5.  Enable and start the service.
6.  Verify application availability.

### Execute Ansible Playbook

Navigate to the Ansible directory:

``` bash
cd ansible
```

Run the playbook:

``` bash
ansible-playbook -i inventory playbook.yml -e "app_port=8082"
```

### Verify Service

``` bash
sudo systemctl status tracker --no-pager
```

### Test Application

``` bash
curl http://localhost:8082/applications
```

The deployment was successfully verified using the systemd service and
HTTP response.

## ☸️ Kubernetes Deployment

Kubernetes is used to deploy and manage multiple replicas of the
application.

### Kubernetes Configuration

  Configuration    Value
  ---------------- --------------------------
  Deployment       `tracker-deployment`
  Service          `tracker-service`
  Replicas         3
  Container Port   8080
  Service Type     NodePort
  NodePort         30080
  Image            `internship-tracker:1.0`

### Apply Kubernetes Configuration

``` bash
kubectl apply -f k8s/
```

### Verify Deployment

``` bash
kubectl get deployments
```

### Verify Pods

``` bash
kubectl get pods -o wide
```

### Verify Services

``` bash
kubectl get services
```

### Access Application

For the Docker Desktop Kubernetes environment, port forwarding was used:

``` bash
kubectl port-forward service/tracker-service 30080:80
```

Keep the command running in one terminal.

In another terminal, test the API:

``` bash
curl http://localhost:30080/applications
```

### Kubernetes Health Monitoring

The deployment configuration includes:

-   Liveness probe using `/actuator/health`.
-   Readiness probe using `/actuator/health`.

These probes help Kubernetes monitor the application's health and
readiness.

### Kubernetes Scaling

The deployment is configured with three replicas.

Check replica availability:

``` bash
kubectl get deployment tracker-deployment
```

Expected result:

``` text
READY   UP-TO-DATE   AVAILABLE
3/3     3            3
```

## 🧪 Testing and Verification

The following operations were successfully verified during project
testing:

  Test                               Result
  ---------------------------------- ---------------
  Spring Boot application startup    Successful
  REST API GET request               Successful
  REST API POST request              Successful
  Jenkins build pipeline             Successful
  Maven compilation                  Successful
  JUnit test stage                   Passed
  Docker image creation              Successful
  Docker container execution         Successful
  Ansible playbook execution         Successful
  Ansible systemd service            Active
  Kubernetes deployment              3/3 available
  Kubernetes pods                    3/3 Running
  Kubernetes service access          HTTP 200 OK
  Kubernetes POST and GET requests   Successful

## 🚀 Future Enhancements

-   Develop a React-based frontend dashboard.
-   Integrate persistent database storage.
-   Implement user authentication and authorization.
-   Automate Docker image publishing to a container registry.
-   Extend Jenkins to perform automated deployment.
-   Deploy the application to a cloud Kubernetes cluster.
-   Integrate monitoring and centralized logging.

## 👨‍💻 Authors

This project was collaboratively developed by:

  Author              GitHub Profile
  ------------------- --------------------------------------------------
  **Swayam Bohara**   [@SWAYAM109](https://github.com/SWAYAM109)
  **Shriya Bhat**     [@Shriya-smart](https://github.com/Shriya-smart)
  **Nysa-20**         [@Nysa-20](https://github.com/Nysa-20)

## 📌 Project Information

-   **Project:** Internship Tracker
-   **Domain:** DevOps and Backend Development
-   **Purpose:** Academic and practical demonstration of CI,
    containerization, configuration management, and container
    orchestration.

------------------------------------------------------------------------

⭐ If you find this project useful, consider giving the repository a
star!

<!-- Jenkins automatic CI trigger verification -->

<!-- Jenkins automatic CI trigger verification 2 -->
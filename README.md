# Internship Application Tracker - CI/CD + Ansible + Kubernetes

REST API (Java 17, Spring Boot, Maven) to track internship applications.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /applications | List all applications |
| POST | /applications | Add an application `{"company","role"}` |
| GET | /applications/{id} | View one application |
| PUT | /applications/{id}/status | Update status (Applied/Interview/Rejected/Offer) |
| GET | /actuator/health | Health check (used by K8s probes) |

Pipeline: Git -> Jenkins (test, build, version, archive) -> Ansible (server) -> Docker (multi-stage) -> Kubernetes (3 replicas, scale to 5, rollback).

Run order: see `scripts/01...07`.

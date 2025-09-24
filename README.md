# Carbon Footprint Tracker — DevSecOps Edition

End‑to‑end project that demonstrates **Development, Testing, Security, DevOps, and Deployment** for interviews and real‑world practice.

## 🍃 What it is
A web app where users log activities (travel, electricity, food, purchases) and get **carbon emissions** estimates, trends, and reduction tips.

## 🧱 Tech Stack
- **Backend:** Spring Boot 3 (Java 17), Spring Web, Spring Security (JWT), Spring Data JPA, PostgreSQL, MapStruct, Lombok
- **Frontend:** React (Vite), React Router
- **DB:** PostgreSQL
- **Container:** Docker (multi‑stage)
- **CI/CD:** GitHub Actions — Build → Test → SAST/Dependency/Container Scans → Package → Deploy
- **Security:** SonarQube (SAST), OWASP Dependency‑Check, Trivy (container/IaC scan), OWASP ZAP Baseline (DAST)
- **Kubernetes:** K8s manifests + optional Ingress
- **IaC:** Terraform (AWS: ECR + RDS + basics). *Edit variables before use.*
- **Monitoring:** Spring Actuator + /actuator/prometheus (Prometheus‑ready), logs to stdout (ELK‑ready)

## 🗺️ Architecture

```
[React/Vite] --(HTTPS)--> [API Gateway/Ingress] --> [Spring Boot API]
                                     |                 |-- JWT Auth
                                     |                 |-- Emission calc
                                     |                 |-- JPA + Postgres
                                     |                 '-- Actuator metrics
                                     '---> [Rate limit / WAF (optional)]
```

## 🧪 Pipeline Stages (GitHub Actions)
1. **Build & Test:** Maven + JUnit
2. **SAST & Lint:** (optional) SonarQube
3. **Dependency Scan:** OWASP Dependency‑Check
4. **Container Scan:** Trivy
5. **Package:** Docker build (multi‑stage)
6. **DAST:** OWASP ZAP Baseline (against staging URL)
7. **Deploy:** to K8s (example uses kubectl; you can switch to Argo CD/GitOps)

## 🚀 Quickstart (Local Dev)
```bash
# 1) bring up services (backend, frontend, postgres)
docker compose up -d --build

# 2) backend API
curl http://localhost:8080/api/health

# 3) frontend
open http://localhost:5173
```

Default creds (dev): `admin@demo.io` / `admin123`

## 📦 Project Structure
```
carbon-footprint-tracker-devsecops/
  backend/
  frontend/
  devops/
    docker/
    k8s/
    terraform/
  .github/workflows/
```

## 🛡️ Security
- JWT auth, role‑based endpoints (`ROLE_ADMIN`, `ROLE_USER`)
- No secrets in code; use `.env` and K8s Secrets
- Images scanned with Trivy; dependencies with OWASP DC
- ZAP baseline scan in CI (configure `STAGING_URL`)

## 📚 API (sample)
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET  /api/activities` (list)
- `POST /api/activities` (create)
- `GET  /api/summary` (emission totals/trends)
- `GET  /actuator/health`

## 🧰 Useful Commands
```bash
# Backend dev
./mvnw spring-boot:run -f backend/pom.xml

# Frontend dev
cd frontend && npm install && npm run dev

# Unit tests
./mvnw test -f backend/pom.xml

# Build docker images
docker build -t local/cft-backend:dev -f devops/docker/Dockerfile.backend .
docker build -t local/cft-frontend:dev -f devops/docker/Dockerfile.frontend .
```

## ☁️ Terraform (AWS) — minimal example
- Creates **ECR repo** for images and **RDS PostgreSQL** (single‑AZ dev). Edit `variables.tf` and **never** commit real secrets.
- For production: add VPC, private subnets, parameter groups, security groups, ALB/Ingress Controller.

---

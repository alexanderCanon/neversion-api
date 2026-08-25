# Neversion API

Backend REST API for the Neversion management platform — handling subscriptions, accounts, profiles, orders, and reservations.

---

## 🚀 Tech Stack

* **Java 21** (Eclipse Temurin / Amazon Corretto)
* **Spring Boot 4** (Spring MVC, Spring Data JPA, Spring Security OAuth2/JWT, Spring Validation)
* **PostgreSQL 16+** with **Flyway** for database migrations
* **OpenAPI 3 / Swagger** (`springdoc-openapi`)
* **Testcontainers & JUnit 5** for automated integration testing
* **Docker** (Multi-stage Alpine-based containerization)
* **Prometheus & Actuator** for observability and metrics

---

## 📋 Prerequisites

* **Java Development Kit (JDK) 21**
* **Docker & Docker Compose** (for database and local containerized workflows)
* (Optional) **PostgreSQL 16+** if running directly on host

---

## ⚙️ Getting Started

### 1. Configure Environment Variables

Copy the example environment file and configure your values:

```bash
cp .env.example .env
```

### 2. Run the Application

#### Option A: Local Development (Maven Wrapper)

**Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```cmd
./mvnw.cmd spring-boot:run
```

#### Option B: Docker Compose

```bash
docker compose -f compose.dev.yml up -d --build
```

---

## 🧪 Testing

Run unit and integration tests (uses Testcontainers for database isolation):

```bash
# Run test suite
./mvnw test

# Full verification build
./mvnw verify
```

---

## 🗄️ Database Migrations

Database schema versioning is managed automatically via **Flyway** on application startup.

To manually run or validate migrations:
```bash
./mvnw flyway:migrate
```

Migration scripts are located in [`src/main/resources/db/migration`](src/main/resources/db/migration).

---

## 📖 API Documentation & Client Generation

* **Swagger UI:** `http://localhost:8080/swagger-ui.html` *(active in development profile)*
* **Generate OpenAPI Specification:**
  ```bash
  ./mvnw test -Dtest=OpenApiExportTest
  ```
  The generated specification will be exported to `target/openapi.json`.

---

## 📊 Monitoring & Health Checks

* **Health Endpoint:** `http://localhost:8080/actuator/health`
* **Prometheus Metrics:** `http://localhost:8080/actuator/prometheus`

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

# SkillSphere Skill Service

## Overview

SkillSphere Skill Service is a microservice responsible for managing skills, user skill tracking, and related analytics within the SkillSphere platform. It provides RESTful APIs for CRUD operations on skills, user skill assignments, and supports integration with other services (such as Auth Service) for a seamless, cloud-native learning and skill tracking experience.

## Tech Stack

- Java 21, Spring Boot 3
- Spring Data JPA (MySQL)
- Spring Security (JWT)
- OpenAPI/Swagger (springdoc-openapi)
- Docker-ready
- Gradle build

## Configuration

All configuration is environment-based. See `src/main/resources/application.properties` for details.

## Running the Service

### Local

```sh
./gradlew bootRun
```

### Docker

```sh
docker build -t skillsphere-skill-service .
docker run --env-file .env -p 8081:8080 skillsphere-skill-service
```

## Authentication (JWT)

- Obtain a JWT by logging in via the Auth Service (`/api/auth/login`).
- Pass the token in the `Authorization` header for all protected endpoints:
  ```
  Authorization: Bearer <your-jwt-token>
  ```

## API Documentation

- Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Example Endpoints

- `GET /api/skills` — List all skills
- `POST /api/skills` — Create new skill (admin only)
- `GET /api/user-skills/user/{userId}` — List all skills for a user
- `POST /api/user-skills` — Assign skill to user

## Error Handling

- All errors are returned as JSON with clear messages and validation details.

---

Further documentation will be added as the service is developed.

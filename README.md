# Users API

A REST API for managing users, built with Spring Boot 4, Java 21, Spring Data JPA and MySQL.

## Features
- Full CRUD (create, read, update with PUT, partial update with PATCH, delete)
- Layered architecture: controller, service, repository, DTOs and mapper
- Request validation with Bean Validation
- Global exception handling with a consistent JSON error shape
- Pagination, sorting and name filtering

## Run locally
Requirements: Java 21 and MySQL 8.

1. Create the database and table:
```sql
CREATE DATABASE users_api;
USE users_api;
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT uk_users_email UNIQUE (email)
);
```
2. Set the database password and start the app:
```bash
export DB_PASSWORD='your-password'
./mvnw spring-boot:run
```

## Endpoints
| Method | Path | Description |
|---|---|---|
| POST | /api/users | Create a user |
| GET | /api/users | List users (`page`, `size`, `sort`, `name`) |
| GET | /api/users/{id} | Get one user |
| PUT | /api/users/{id} | Replace a user |
| PATCH | /api/users/{id} | Update some fields |
| DELETE | /api/users/{id} | Delete a user |

Example: `GET /api/users?name=mar&page=0&size=5&sort=name,asc`

## Error format
```json
{ "status": 404, "error": "Not Found", "message": "User with id 9 not found", "time": "2026-11-13T15:43:51Z" }
```
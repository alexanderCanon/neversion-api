# System Architecture: Neversion E-commerce

# High-Level Architecture & Tech Stack
The system operates on a Hybrid Architecture separating the Administrator environment from the Store environment.

We focus solely on the backend with Spring Boot, so our **ultimate goal** is to have endpoints ready for use in production.
- Database: Supabase (PostgreSQL).
- Authentication: Supabase Auth (handles Login and JWT generation).
---

1.1 Backend (Admin Scope)
- Responsibility: Full control over all entities and manage relationships.
- Operational tables, such as orders and inventory, require manual sales processing.

## Tech Stack
**Programming Language:** Java 17
**Framework:** Spring
**Tool:** Spring Boot 3.5.10
**Build Tool:** Maven
**Database:** PostgreSQL
**ORM:** Spring Data JPA + Hibernate
**Validation:** Spring Boot Starter Validation (Jakarta)
**Authentication:** Supabase Auth (JWT)
**Security:** Spring Security 6
**Serialization:** Spring Boot Starter JSON (Jackson)
**Boilerplate:** Lombok (Builder, Getter and Setter annotations)

## Testing
JUnit y Mockito for Test. Use assertThat from assertJ

```
import static org.assertj.core.api.Assertions.*;
```

## Application Configuration
By default dev profile is active.

## Environment
Don't read any **.env** file

## API Endpoints
/api/v1/...

## Applied Architecture Principles
1. Independent domain
2. Ports as contracts
   - In: defines what the application can do
   - Out: defines what the application needs from the outside (Use Cases)
3. Interchangeable adapters
   - REST Adapter: implements the HTTP interface
   - JPA: implements the repository port
4. Dependency flow
   - Controller -> UseCase (Port In) -> Service -> Port Out <- JpaAdapter
5. Separate mappers
   - DTO <-> Domain, Entity <-> Domain. No MapStruct, Yes Builder

## Important Notes
1. **Validation:** Use Jakarta Validation for DTORequest, NotBlank for String fields, and NotNull for other applicable fields
2. **Security:** CSRF is disabled (for now)
3. **Records:** Use Java Records for Request/Response DTOs
4. **Soft delete:** The entity uses SQLDelete and SQLRestriction

## Structure
src/main/java/com/example/project
│
├── config/ (Security global config)
│
├── exceptions/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── CustomException.java
│
└── <feature-name>/
    ├── domain/
    │   ├── model/
    │   │   ├── <AggregateName>.java
    │   │   └── <EnumName>.java
    │   │
    │   ├── port/
    │   │   └── out/
    │   │       └── <Feature>RepositoryPort.java
    │   │
    │   └── service/
    │       └── <DomainService>.java
    │
    ├── application/
    │   ├── port/
    │   │   └── in/
    │   │       └── <UseCaseName>.java
    │   │
    │   └── service/
    │       └── <UseCaseName>Service.java
    │
    └── infrastructure/
        ├── config/
        │   └── SecurityConfig.java
        │
        └── adapters/
            ├── in/
            │   └── rest/
            │       ├── <Feature>Controller.java
            │       │
            │       ├── dto/
            │       │   ├── <RequestDTO>.java
            │       │   └── <ResponseDTO>.java
            │       │
            │       └── mapper/
            │           └── <RestMapper>.java
            │
            └── out/
                ├── <Feature>Entity.java
                ├── SpringData<Feature>Repository.java
                ├── Jpa<Feature>Adapter.java
                └── <Feature>PersistenceMapper.java


# Spring Boot Project Ecommerce "Neversion"

## Description
The project is for panel administration by Admin.

## Business Context
The backend mainly manages products (digital products) with categories.

### Main Entity: **Product**
This is the main product sold in the ecommerce.

## Stack Tecnológico
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
**Boilerplate:** Lombok

## First Step
Review the pom.xml to ensure we have the correct dependencies, later review the STRUCTURE.md file to understand the architecture.

## Inject Dependencies
With constructor, no Autowired annotation

## Testing
JUnit y Mockito for Test. Use assertThat from assertJ

```
import static org.assertj.core.api.Assertions.*;
```

## Application Configuration
By default dev profile is active. Don't read my application-dev.yaml because environment variables are defined there.

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
3. **Records:** Use Java Records for Request/Response DTOs (if you think a class could be a Record, use it to avoid boilerplate)
4. **Soft delete:** The entity uses SQLDelete and SQLRestriction

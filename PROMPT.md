# TASK: Backend Error Handling Audit & Standardization

## Context & Goal
I am reviewing the error handling strategy of my Spring Boot REST API (Hexagonal Architecture + DDD). Currently, I want to avoid generic `500 Internal Server Errors` and ensure the API returns standardized, RFC 7807 compliant (or similar clean JSON) error responses to the Angular frontend.

## 1. Required HTTP Status Code Mapping
The API must strictly use the following HTTP codes based on the context:
- `400 Bad Request`: Field validation errors (e.g., `@Valid` failures).
- `401 Unauthorized` / `403 Forbidden`: Spring Security & JWT role failures.
- `404 Not Found`: When querying a DB ID that does not exist.
- `409 Conflict`: Domain/Business Rule violations (e.g., "Account slot is already occupied", "Max profiles exceeded").
- `500 Internal Server Error`: Unhandled runtime exceptions (must mask the stack trace from the client).

## Your Tasks

**Task 1: Audit Current Codebase**
Scan my current Application Services (Use Cases) and REST Controllers. Identify:
1. Are there custom Domain Exceptions already created? (e.g., `ResourceNotFoundException`, `BusinessValidationException`).
2. How are validation errors (`MethodArgumentNotValidException`) currently handled?
3. Provide a brief diagnosis of what is missing.

**Task 2: Design the Global Exception Handler (`@RestControllerAdvice`)**
After the audit, propose the code for:
1. A standard `ErrorResponse` record/DTO. It must contain: `timestamp`, `status`, `error` (HTTP string), `message` (human-readable), and `path`.
2. A `@RestControllerAdvice` class that catches:
   - `EntityNotFoundException` (or custom 404 equivalent).
   - Domain-specific exceptions (mapped to 409 Conflict).
   - `MethodArgumentNotValidException` (mapped to 400 Bad Request, returning the specific fields that failed).
   - `Exception.class` (Fallback for 500, logging the real error but returning a safe message).

**Task 3: Example Integration**
Show me an example of how one of my Domain models or Use Cases (e.g., creating a Subscription or allocating a Slot) should throw a `DomainRuleException` so the `@RestControllerAdvice` catches it and returns a `409 Conflict`.

Wait for my approval on the `ErrorResponse` format before generating the full Java implementation.
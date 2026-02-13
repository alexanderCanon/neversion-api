# System Architecture: Neversion E-commerce

# 1. High-Level Architecture & Tech Stack
The system operates on a Hybrid Architecture separating the Administrator environment from the Client environment.

We focus solely on the backend with Spring Boot, so our **ultimate goal** is to have endpoints ready for use in production.
- Database: Supabase (PostgreSQL).
- Authentication: Supabase Auth (handles Login and JWT generation).
---

1.1 Backend (Admin Scope)
- Framework: Spring Boot 3.5.10
- Pattern: Hexagonal Architecture (Adapters and Ports), packages structures is at **STRUCTURE.md** file
- DB Connection: Direct JDBC/R2DBC connection (Host, User, Password, Database) at application-dev.yaml (don't read this file).
- Security: Uses Supabase JWT for request authentication (Bearer Token) via **Spring Security**.
- Responsibility: Full control over all entities.

¿How Client Backend and Frontend works? JUST FOR REFERENCE
1.2 Frontend (Client Scope) Not applicable to this project, but keep it in mind.
- Library: Supabase Client (supabase-js).
- Connection: Direct connection to Supabase via API Publishable Key.
- Security: Row Level Security (RLS) + Policies.
- Responsibility: Public store view, user registration, and order placement.
---

# 2. Global Database Standards
- CRUD Scope: Currently, the system focuses on CREATE, READ, and SOFT DELETE.
- UPDATE Policy: No logical updates are permitted in this phase, except for toggling is_active flags.
- Soft DELETE: Implemented via the is_active boolean column.
- Timestamptz: All creation timestamps default to now().
---

# 3. Database Schema: Management Module (Core)
## Table 0: public.profiles

- Description: A mirror of Supabase auth.users. Data is inserted via a database trigger upon user signUp.
- Access: Read-Only (except is_active).

| Column      | Type        | Constraints              | Description                          |
|------------|------------|--------------------------|--------------------------------------|
| id         | UUID       | PK, FK                   | References auth.users.               |
| name       | VARCHAR    |                          | Customer first name.                 |
| lastname   | VARCHAR    |                          | Customer last name.                  |
| email      | VARCHAR    |                          | Customer email.                      |
| phone      | VARCHAR    |                          | Customer phone.                      |
| is_active  | BOOLEAN    | DEFAULT true             | Soft delete flag.                    |
| created_at | TIMESTAMPTZ| DEFAULT now()            | Auto-generated timestamp.            |

### Business Logic:
- Search: By id, name, email, is_active.
- Response Payload: name, lastname, email, phone, is_active.

---
## Table 1: services

- Description: Catalog of digital services sold.

| Column      | Type        | Constraints      | Description                              |
|------------|------------|------------------|------------------------------------------|
| id         | INT        | PK, AUTO-INCREMENT |                                          |
| name       | VARCHAR    | NOT NULL         | Service name.                            |
| description| TEXT       |          |                                          |
| image_url  | VARCHAR    | NULLABLE         | Raw link (e.g., AWS Bucket).             |
| is_active  | BOOLEAN    | DEFAULT true     | Soft delete flag.                        |
| created_at | TIMESTAMPTZ| DEFAULT now()    |                                          |

### Business Logic:
- Search: By id, name, is_active.
- Response Payload: id, name, description, is_active.

---
## Table 2: categories

- Description: Fixed categories for services.
- Predefined Values: platforms (Streaming), combos (Bundles), gift card (Digital codes), recharges (Games), suscriptions (SaaS/Tools).

| Column      | Type | Constraints         | Description     |
|------------|------|--------------------|-----------------|
| id         | INT  | PK, AUTO-INCREMENT |                 |
| name       | VARCHAR | NOT NULL        | Category name.  |
| description| TEXT |                    |                 |

### Business Logic:
- Search: By id, name.
- Constraints: No soft delete.
- Response Payload: All columns except id.

## Table 3: services_details
- Description: Linking table defining the specific offering (Service + Category + Price).

| Column            | Type    | Constraints         | Description                          |
|------------------|---------|--------------------|--------------------------------------|
| id               | BIGINT  | PK, AUTO-INCREMENT |                                      |
| service_id       | INT     | FK                 | References services(id).             |
| category_id      | INT     | FK                 | References categories(id).           |
| price_individual | NUMERIC | NULLABLE           | Price per profile/unit.              |
| price_familiar   | NUMERIC | NULLABLE           | Price for full account.              |

### Business Logic:
- Search: By service.name, category.name.
- Constraints: FKs selected via list. Prices manual entry. No soft delete.
- Response Payload: All columns except id.

## Table 4: users_guests
- Description: Stores data for anonymous buyers (no registered account).

| Column      | Type        | Constraints        | Description           |
|------------|------------|--------------------|-----------------------|
| id         | UUID       | PK, AUTO-GENERATED |                       |
| name       | VARCHAR    | NOT NULL           |                       |
| email      | VARCHAR    | NOT NULL           |                       |
| phone      | VARCHAR    | NOT NULL           |                       |
| is_active  | BOOLEAN    | DEFAULT true       | Soft delete flag.     |
| created_at | TIMESTAMPTZ| DEFAULT now()      |                       |

### Business Logic:
- Search: By id, name, phone.
- Response Payload: id, name, email, phone, is_active.

## Table 5: credentials
- Description: Repository of digital assets (accounts/passwords) to be sold.

| Column             | Type    | Constraints         | Description                                   |
|-------------------|---------|--------------------|-----------------------------------------------|
| id                | BIGINT  | PK, AUTO-INCREMENT |                                               |
| email             | VARCHAR | NOT NULL           | Service account email (Can repeat).           |
| pass              | VARCHAR | NOT NULL           | Plaintext password (Low risk).                |
| is_active         | BOOLEAN | DEFAULT true       | Soft delete flag.                             |
| service_details_id| BIGINT  | FK                 | References services_details(id).              |

### Business Logic:
- Search: By id, email, is_active.
- Response Payload: All columns.

## Table 6: inventory
- Description: Admin procurement records (Costs, Suppliers, Expiry).

| Column           | Type           | Constraints         | Description                                      |
|------------------|---------------|--------------------|--------------------------------------------------|
| id               | BIGINT        | PK, AUTO-INCREMENT |                                                  |
| credentials_id   | BIGINT        | FK                 | References credentials(id).                      |
| seller           | VARCHAR       | NOT NULL           | Supplier name (Informational).                   |
| price_seller     | NUMERIC(10,2) | NOT NULL           | Cost of goods (> 0).                             |
| stock            | INT           | DEFAULT 1          | Varies by demand.                                |
| account_type     | ENUM          | NOT NULL           | Values: 'familiar', 'individual'.                |
| expiration_date  | DATE          | NOT NULL           | Supplier expiry date (>15 days from now).        |
| is_active        | BOOLEAN       | DEFAULT true       |                                                  |
| created_at       | TIMESTAMPTZ   | DEFAULT now()      |                                                  |

### Business Logic:
- Search: By id, seller, account_type, expiration_date, is_active.
- Response Payload: id, credentials.email, credentials.pass, seller, price_seller, account_type, expiration_date, is_active.

## Table 7: subscriptions
- Description: The Source of Truth for active customer services. Displays on the main dashboard.

| Column         | Type     | Constraints            | Description                                              |
|---------------|----------|-----------------------|----------------------------------------------------------|
| id            | BIGINT   | PK, AUTO-INCREMENT    |                                                          |
| profiles_id   | UUID     | FK, NULLABLE          | References profiles(id).                                 |
| user_guest_id | UUID     | FK, NULLABLE          | References users_guests(id).                             |
| credentials_id| BIGINT   | FK                    | References credentials(id).                              |
| purchase_date | DATE     | NOT NULL              | Date of record creation.                                 |
| renewal_date  | DATE     | NOT NULL              | Expiry date (>10 days from purchase).                    |
| profile       | VARCHAR  | NULLABLE              | Profile name (if individual).                            |
| pin           | VARCHAR  | NULLABLE              | Profile PIN (if individual).                             |
| is_active     | BOOLEAN  | DEFAULT true          |                                                          |
| s_status      | ENUM     | DEFAULT 'active'      | Values: 'active', 'inactive', 'suspended'.               |

## Business Logic:
- Constraints:
- - Ownership: Either profiles_id OR user_guest_id must be present (NOT NULL constraint on the logical set).

- - Nullables: profile and pin can be null if account_type is 'familiar' (full account).
- Search: By id, profiles.name, users_guests.name, credentials.email, service_detail, dates, status.
- Response Payload: All columns are required.
---

# 4. Database Schema: Operations Module (Secondary)
These tables handle the transaction flow. Currently, they are for Operations and future testing, but must be modeled now.

## Table 8: bookings
- Description: Temporary reservations created before payment.

| Column              | Type         | Constraints         | Description                                             |
|--------------------|-------------|--------------------|---------------------------------------------------------|
| id                 | BIGINT      | PK, AUTO-INCREMENT |                                                         |
| services_details_id| BIGINT      | FK                 | References services_details(id).                        |
| user_guest_id      | UUID        | FK                 | References users_guests(id) (Guest flow assumed).       |
| quantity           | INT         | DEFAULT 1          |                                                         |
| booking_state      | ENUM        | NOT NULL           | 'pending', 'expired', 'converted', 'aborted'.           |
| expiration_date    | TIMESTAMPTZ | NOT NULL           | Logical duration: 1 hour.                               |
| created_at         | TIMESTAMPTZ | DEFAULT now()      |                                                         |

### Business Logic:
- Admin does not manually interact.
- System requires Cron Jobs to purge 'converted' or 'expired' records.

## Table 9: orders
- Description: Finalized transaction record (Who and When). Created when booking converts.

| Column        | Type           | Constraints         | Description                                   |
|--------------|---------------|--------------------|-----------------------------------------------|
| id           | BIGINT        | PK, AUTO-INCREMENT |                                               |
| profile_id   | UUID          | FK, NULLABLE       |                                               |
| user_guest_id| UUID          | FK, NULLABLE       |                                               |
| total        | NUMERIC(10,2) | NOT NULL           |                                               |
| proof_url    | VARCHAR       | NULLABLE           | Payment receipt image (Manual validation).   |
| notes        | TEXT          | NULLABLE           | User suggestions.                            |
| created_at   | TIMESTAMPTZ   | DEFAULT now()      |                                               |

### Business Logic:
- Ownership: Either profile_id or user_guest_id is required.
- Response Payload: All columns.

## Table 10: order_details
- Description: Line items of the order (What and How much).

| Column             | Type           | Constraints         | Description                                  |
|-------------------|---------------|--------------------|----------------------------------------------|
| id                | BIGINT        | PK, AUTO-INCREMENT |                                              |
| order_id          | BIGINT        | FK                 | References orders(id).                       |
| service_details_id| BIGINT        | FK                 | References services_details(id).             |
| quantity          | INT           | DEFAULT 1          |                                              |
| unit_price        | NUMERIC(10,2) | NOT NULL           | Snapshot of price at purchase time.          |
| subtotal          | NUMERIC(10,2) | NULLABLE           | Calculated field.                            |
| purchase_date     | TIMESTAMPTZ   | DEFAULT now()      |                                              |

### Business Logic:
- Validation: unit_price must be > 0 and match services_details prices.
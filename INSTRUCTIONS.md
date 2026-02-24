# System Architecture: Neversion E-commerce

# 1. High-Level Architecture & Tech Stack
The system operates on a Hybrid Architecture separating the Administrator environment from the Store environment.

We focus solely on the backend with Spring Boot, so our **ultimate goal** is to have endpoints ready for use in production.
- Database: Supabase (PostgreSQL).
- Authentication: Supabase Auth (handles Login and JWT generation).
---

1.1 Backend (Admin Scope)
- Responsibility: Full control over all entities and manage relationships.

# 2. Database Schema: Management Module (Core)
## Table 0: public.profiles

- Description: A mirror of Supabase auth.users. Data is inserted via a database trigger upon user signUp.
- Access: Read-Only (except is_active).

| Column      | Type      | Constraints              | Description                          |
|------------|------------|--------------------------|--------------------------------------|
| id         | UUID       | PK, FK                   | References auth.users.               |
| name       | VARCHAR    |                          | Customer first name.                 |
| lastname   | VARCHAR    |                          | Customer last name.                  |
| email      | VARCHAR    |                          | Customer email.                      |
| phone      | VARCHAR    |                          | Customer phone.                      |
| is_active  | BOOLEAN    | DEFAULT true             | Soft delete flag.                    |
| created_at | TIMESTAMPTZ| DEFAULT now()            | Auto-generated timestamp.            |

- Response Payload: name, lastname, email, phone, is_active.

---
## Table 1: services

- Description: Catalog of digital services sold.

| Column      | Type      | Constraints      | Description                              |
|------------|------------|------------------|------------------------------------------|
| id         | INT        | PK, IDENTITY |                                          |
| name       | VARCHAR    | NOT NULL         | Service name.                            |
| description| TEXT       |          |                                          |
| image_url  | VARCHAR    | NULLABLE         | Raw link (e.g., AWS Bucket).             |
| is_active  | BOOLEAN    | DEFAULT true     | Soft delete flag.                        |
| created_at | TIMESTAMPTZ| DEFAULT now()    |                                          |
| category   | category_type    | NOT NULL         | Category name.                           |

- Response Payload: id, name, description, is_active.

---

## Table 2: service_items
- Description: Linking table defining the specific offering (Service + Price).

| Column            | Type    | Constraints         | Description                          |
|------------------|---------|--------------------|--------------------------------------|
| id               | BIGINT  | PK, AUTO-INCREMENT |                                      |
| service_id       | INT     | FK                 | References services(id).             |
| price            | NUMERIC | NULLABLE           | Price per profile/unit.              |
| duration         | VARCHAR     | NULLABLE           | Duration in days.                  |
| account_type     | account_type | NOT NULL | Describes 'familiar' or 'individual' |

- Response Payload: Managed by Sservice (father Class).

## Table 3: users_guests
- Description: Stores data for anonymous buyers (no registered account).

| Column      | Type        | Constraints        | Description           |
|------------|------------|--------------------|-----------------------|
| id         | UUID       | PK, AUTO-GENERATED |                       |
| name       | VARCHAR    | NOT NULL           |                       |
| email      | VARCHAR    | NOT NULL           |                       |
| phone      | VARCHAR    | NOT NULL           |                       |
| is_active  | BOOLEAN    | DEFAULT true       | Soft delete flag.     |
| created_at | TIMESTAMPTZ| DEFAULT now()      |                       |

- Response Payload: id, name, email, phone, is_active.

## Table 4: accounts
- Description: Admin procurement records (Costs, Suppliers, Expiry).

| Column           | Type           | Constraints         | Description                                      |
|------------------|---------------|--------------------|--------------------------------------------------|
| id               | BIGINT        | PK, AUTO-INCREMENT |                                                  |
| email             | VARCHAR | NOT NULL           | Service account email (Can repeat).           |
| pass              | VARCHAR | NOT NULL           | Plaintext password (Low risk).                |
| service_id | INTEGER | NOT NULL | Service |
| seller           | VARCHAR       | NOT NULL           | Supplier name (Informational).                   |
| price_seller     | NUMERIC(10,2) | NOT NULL           | Cost of goods (> 0).                             |
| stock            | INT           | DEFAULT 1          | Varies by demand.                                |
| account_type     | ENUM          | NOT NULL           | Values: 'familiar', 'individual'.                |
| expiration_date  | DATE          | NOT NULL           | Supplier expiry date (>15 days from now).        |
| is_active        | BOOLEAN       | DEFAULT true       |                                                  |
| created_at       | TIMESTAMPTZ   | DEFAULT now()      |                                                  |

### Business Logic:
- Search: By id, seller, account_type, expiration_date, is_active.
- Response Payload: id, email, pass, seller, price_seller, account_type, expiration_date, is_active.

## Table 5: subscriptions
- Description: The Source of Truth for active customer services. Displays on the main dashboard.

| Column         | Type     | Constraints            | Description                                              |
|---------------|----------|-----------------------|----------------------------------------------------------|
| id            | BIGINT   | PK, AUTO-INCREMENT    |                                                          |
| profiles_id   | UUID     | FK, NULLABLE          | References profiles(id).                                 |
| user_guest_id | UUID     | FK, NULLABLE          | References users_guests(id).                             |
| account_id| BIGINT   | FK                    | References credentials(id).                              |
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
- Response Payload: All columns are required.
---

From point #4 will be implemented after Administratives modules
# 4. Database Schema: Operations Module (Secondary)
These tables handle the transaction flow. Currently, they are for Operations and future testing, but must be modeled now.

## Table 6: bookings
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

## Table 7: orders
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

## Table 8: order_details
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
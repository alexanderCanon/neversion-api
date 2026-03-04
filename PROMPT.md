# General instructions
The previous flow already handles `Reservations` -> `Orders`. Now we need to handle the manual assignment of credentials (`Accounts`) to those orders, creating a `Subscription`.

**Important Context:**
- All operations relate to `user_guest_id`.
- Accounts can be 'familiar' (shared among profiles) or 'individual' (exclusive).

### Task 1: Entity & Repository
Create the JPA Entity for `Subscription` based on this DB schema:
`id (UUID), order_detail_id (UUID), user_guest_id (UUID), account_id (UUID), purchase_date (DATE), renewal_date (DATE), profile (VARCHAR), pin (VARCHAR), status (ENUM: active, expired, cancelled, suspended), is_active (BOOLEAN), created_at (TIMESTAMPTZ)`.
*Create the corresponding Spring Data JPA Repository.*

### Task 2: The Service Layer (Business Logic & Constraints)
Create a `SubscriptionService` with a method to manually assign an account to an order:
`public Subscription assignAccountToOrder(CreateSubscriptionDTO dto)`
**Crucial Business Rule (Anti-Overbooking):** Before saving, the service MUST query the database to check if the `account_id` being assigned belongs to an `account_type == 'individual'`. If it is 'individual', it must verify that there are NO currently 'active' subscriptions for this `account_id`. If there is, throw a custom `BusinessRuleException` (HTTP 409 Conflict). Do this validation carefully inside a `@Transactional` block.

### Task 3: The Data Transfer Object (DTO) & Custom Query
We need a unified View/DTO for the Admin Dashboard. Create a `SubscriptionDashboardDTO` containing EXACTLY these fields:
`String email, String password, String profile, String pin, String serviceName, LocalDate purchaseDate, LocalDate renewalDate, String status`
*Note: `email` and `password` come from the `Account` entity. `serviceName` comes from `Product` (navigated via OrderDetail -> Inventory -> Product).*
*Write the custom `@Query` (JPQL or native) in the Repository to fetch a `List<SubscriptionDashboardDTO>` efficiently without N+1 query problems.*

Write clean, modular, and production-ready Java code. Include comments explaining the anti-overbooking logic and the JPQL join fetch strategy.



# TASK: Domain Model Audit and Refactoring Plan (Sprint 1)

## Context & Goal
I have updated the business analysis, workflows, and database schema for my e-commerce backend. 

Your goal is to perform a gap analysis: identify what changes and what needs to be refactored in my entities/use-cases, and what endpoints are missing. If no changes are needed, say so.
---
## 1. Business Overview (Sprint 1 MVP)
The system is an e-commerce platform for reselling digital products (streaming platforms, recharges, etc.). For Sprint 1, the core goal is to digitize the Admin's manual operations (currently in Excel/WhatsApp) and provide a basic storefront MVP for customers (operating mainly as guests). 

**Crucial Strategy:** The business operates **on-demand**. There is no "real" physical stock reserved at checkout. Accounts are purchased by the Admin from external providers *after* the customer pays.
---

## 2. Core Workflows

### Storefront Flow (Customer)
1. **Browse:** Customer views the catalog (`products` and specific `inventory` variants).
2. **Reserve:** Customer creates a temporary **Reservation** as a guest (`user_guests`), providing name, email, and phone. 
   - *Rule:* Reservations have a 1-hour expiration timer. No actual account is blocked or assigned yet.
3. **Payment & Order:** Customer pays manually (bank transfer) and uploads a receipt. The system uploads the image to an S3 bucket, saves the `receipt_url`, and generates a persistent **Order** linked to the Reservation, setting the status to `pending_validation`.

### Backoffice Flow (Admin)
1. **Validate:** Admin reviews pending Orders and validates the payment receipt.
2. **Procure (Manual):** Admin manually buys the requested account from their external provider (seller).
3. **Register Stock:** Admin registers the new master credential in the system as an **Account** (`accounts`). The system auto-generates the available physical slots (`account_slots`) based on the `inventory` rules (e.g., Netflix = 5 slots).
4. **Fulfill & Subscribe:** Admin assigns an `account_slot` (or the full `account`) to the customer's Order. The Order becomes `completed`, and the system generates a **Subscription** (`subscriptions`) linking the customer, the slot, and the start/end dates for future billing.

---

## 3. Key Business Rules & Validations
- **Catalog vs. Inventory:** `products` defines the general item (e.g., "Netflix"). `inventory` defines the specific sellable variant (e.g., "Individual Profile, 30 days, max 5 profiles, GTQ5.00").
- **Guest vs. Registered Users:** The system must support `user_guests` (Sprint 1) and `profiles` (Supabase Auth users, Sprint 2 not profiles repository, is not neccessary for now). Transactions (Reservations, Orders, Subscriptions) must link to either one or the other using mutually exclusive constraints.
- **Resource Allocation:** A Subscription can be linked to a single `account_slot` (for individual profile sales) OR directly to the root `account` (if the entire account was sold).
---

## 4. Target Database Schema (PostgreSQL DDL)
You can check the schema by mi migration files in `src/main/resources/db/migration`

## 5. Redundant Info
If you consider that I am being redundant or if my intentions are not clear, let me know honestly and strictly professionally; scold me.

```markdown
Now I need the following:
Verify if my endpoints are well-documented with OpenAPI and if they comply with minimum security requirements.
Additionally, add JSON files within the `jsonfiles` package in `resources` to perform real tests with my entire system.
```
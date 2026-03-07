# General Business Context
This is general context about business, validations, use cases, etc. But it can be updated as the project iterates, because the instructions are in PROMPT.md file, then if this context is not updated yet, then update it.

## General Description
I sell digital products, mainly streaming services like Netflix, HBO, Disney, etc. I don't sell physical products. Everything I deliver are digital accesses, accounts, or profiles within those accounts.

My business model is manual. The customer enters, chooses one or several services, makes payment by bank transfer, and then sends me the receipt. I manually check if the payment is correct and then send the accesses (email, password, profile, pin) via WhatsApp or email.

I work under these rules:
- When the customer chooses their services, the system saves the price for 1 hour.
- They have 60 minutes to pay and upload their receipt.
- If they don't pay within that time, the order is automatically cancelled.
- When they upload the receipt, the order remains pending until I review it.
- Only after validating the payment do I send them the accesses.
- The customer can buy as a guest (only leaving name, email, and phone) or can register to see their active services in their panel later.
- I buy accounts from providers. Some accounts are:
    - Individual (can only be sold to one person).
    - Family (I can sell the same accesses to multiple people if it's by profile, or the whole account).
- When I deliver a service, I want it to be clear:
    - What the customer bought.
    - For how long (30, 60, or 90 days).
    - When it starts and when it ends.
    - What access credentials I delivered exactly.
- I also want to prevent fraud, for example:
    - The same payment receipt being used twice.
    - An individual account being sold to more than one person at the same time.
- In the future I want to be able to:
    - Offer discounts when they buy multiple services.
    - Know how much I really earn by comparing what I pay providers vs what I sell.
    - Clearly see which services are active, expired, or about to expire.
- I want any solution you propose to be:
    - Simple to operate.
    - Secure.
    - Easy to scale.
    - Designed for growth.
    - Adapted to a business where validation is manual.
- Before proposing changes or improvements, analyze my business model and respect this operating logic.

## Use Cases and Business Rules

### Business Rules

These are the logical guidelines that the backend code must strictly follow:

- **BR-01: Reservation Validity.** Every created reservation will have an exact lifespan of 60 minutes. If it expires without the customer uploading the proof of payment, the reservation status automatically changes to `cancelled`.
- **BR-02: Price Freezing.** When creating a reservation, the system must record the current inventory price at that exact millisecond. Subsequent changes to the catalog price will not affect existing reservations.
- **BR-03: Combo Discount.**
    - If the cart has **1** service: List price is charged (0% discount).
    - If the cart has **2 or more** services: An automatic discount of **2%** (or the tiered percentage defined by the client) is applied to the subtotal of the applicable products.
- **BR-04: Discount Rule.** If an inventory product is created with a duration of "90 days" or more, the system must calculate a 3% discount on the proportional monthly base price. That is, products with 30-day duration always have the price defined dynamically.
- **BR-05: Proof Anti-Fraud.** The same payment proof link or hash (`proof_url`) cannot be associated with more than one reservation in the system.
- **BR-06: Individual Account Exclusivity.** An `account_id` of type `individual` cannot be assigned to more than one subscription with `active` status at the same time.
- **BR-07: Reservation to Order Transition.** An order can only be created if a reservation with `uploaded` status is manually validated by the Administrator.

Observation, there are two types of discounts:
- **Discount Rule:** This discount is applied to accounts depending on the duration.
- **Combo Discount:** This discount is applied to the subtotal of the applicable products.

### Use Cases

### Actor: ADMIN

- [x] **CU-A01: Manage Catalog (Theoretical Inventory).** The admin can create, read, update, or deactivate products (`products`) and their different variants (`inventory` - type, duration, price).
- [x] **CU-A02: Manage Raw Material (Physical Inventory).** The admin can register the actual credentials (`accounts`) purchased from providers, specifying email, password, cost (`price_seller`), type, and expiration date.
- [x] **CU-A03: Validate Payments (Reservation Flow).** The admin can view a list of reservations in `uploaded` status, open the payment receipt link (`proof_url`), and decide whether to approve it (creating the `order` and generating the subscription) or reject it (changing status to `cancelled`).
- [ ] **CU-A04: Subscription Monitoring.** The admin can view a master table with all subscriptions. They must be able to filter by: status (active, expired, suspended), specific customer, or specific product, clearly visualizing the start and end dates (`purchase_date`, `renewal_date`).
- [ ] **CU-A05: Order Fulfillment and Account Assignment.** The Admin reviews a reservation in `uploaded` status and approves the payment. The system, within a transaction, updates the reservation status and creates the `order` and `order_details`. The Admin then selects an available account (`accounts`) from the inventory, enters the Profile/PIN, and assigns it. The system validates individual account exclusivity (BR-06) before creating the `subscription`.
- [ ] **CU-A06: Subscription Lifecycle Management.** The Admin can manually update subscription details (Profile, PIN), suspend or terminate access, and manage renewals or status changes for existing subscriptions.
- [ ] **CU-A07: Master View.**
    - Actor: Administrator (and Customer in the future).
    - Flow: The admin accesses a control table. The system performs a JOIN between `subscriptions`, `accounts`, `users_guests`, and `order_details` -> `inventory` -> `products` to build the view.
    - Required Output Data (DTO): Email | Password | Profile | PIN | Service (Product Name) | Purchase Date | Renewal Date | Status.

### Actor: CUSTOMER

- [ ] **CU-C01: Explore Catalog.** The customer can view all active products and select variants (e.g., Netflix, Individual, 30 days) while seeing the price in real-time.
- [ ] **CU-C02: Build Cart and Apply Combos.** The customer can add multiple services to the cart. The system must calculate and display the discount applied in real-time if the customer selects 2 or more services.
- [ ] **CU-C03: Generate Reservation (Checkout).** The customer can enter as a guest (name, email, phone), upload their payment receipt, and generate the reservation. The system will show a timer (optional in UI) and confirm that their order is "Pending validation".
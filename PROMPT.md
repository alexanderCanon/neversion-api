# General instructions
Business context for this phase: > The customer enters, sees the catalog, chooses products (digital services) and creates a "Reservation" (a temporary cart). When creating the reservation, they must attach the payment proof (proof_url). The system must save the price at that moment and give the reservation a lifespan of exactly 60 minutes.

Your task is to program the first increment (Phase 1):

### Package: reservations (previously called booking, update fields and attributes with the new name where applicable)
- Create the controller and route for POST /api/reservations.
- Strict rules for the POST /api/reservations endpoint:

Must receive in the body: guest customer data (name, email, phone), an array of items (inventory_id, qty) and the proof_url.
Must validate that the proof_url does not already exist in the database (to prevent fraud).
Must fetch the current price from the inventory table and save it as unit_price in reservation_details to freeze the price.
Must calculate and insert the expiration date (expiration_date) by adding exactly 60 minutes to the current time.
The entire creation operation (user, reservation and details) must be done within a Database Transaction to ensure integrity.

**Discount Rule**: If an inventory product is created with a duration of "90 days" or more, the system must calculate a 3% discount on the proportional monthly base price. That is, products with 30-day duration always have the price defined dynamically.


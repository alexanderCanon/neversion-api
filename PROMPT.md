# General instructions
Product managing and its inventory, first catalog product must be created, then his variants in inventory, price, stock and more info (you'll find inside Domain Model)

## Tech Instructions
My operative system is Windows 11, so we use Powershell for commands.

## Tables
| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | IDENTITY, PK |
| name | VARCHAR(255) | NOT NULL, MIN 3 |
| description | TEXT | NULLABLE |
| image_url | VARCHAR(255) | NULLABLE |
| category | category_type (ENUM) | platform, recharge, giftcard, subscription |

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | IDENTITY, PK |
| product_id | BIGINT | FK |
| price | NUMERIC(10,2) | NOT NULL, DEFAULT 0.00 |
| duration | VARCHAR(100) | NOT NULL, "X days" |
| account_type | account_type (ENUM) | familiar, individual |
| stock | INT | NOT NULL, DEFAULT 0, > 0 |


## Tablas
| id | BIGINT | IDENTITY, PK | | --- | --- | --- | | name | VARCHAR(255) | NOT NULL, MIN 3 | | description | TEXT | NULLABLE | | image_url | VARCHAR(255) | NULLABLE | | category | category_type (ENUM) | platform, recharge, giftcard, subscription |

| id | BIGINT | IDENTITY, PK | | --- | --- | --- | | product_id | BIGINT | FK | | price | NUMERIC(10,2) | NOT NULL, DEFAULT 0.00 | | duration | VARCHAR(100) | NOT NULL, “X days” | | account_type | account_type (ENUM) | familiar, individual | | stock | INT | NOT NULL, DEFAULT 0, > 0 |

The final objective is to have the following endpoints:
1. Create a product and another to retrieve all or by ID
2. Add inventory details referencing the product by its id, for add and get all operations (no need to retrieve individual records by ID)
3. Below you will find the use cases which could require their own endpoint

**Business Rules:**
Products:
- name minimum 3 characters
- category mandatory
- no duplicate names (if you decide so)
- cannot be deleted if it has active inventories

Inventory:
- price never negative
- stock never negative
- duration cannot be empty
- product must exist

**Discount Rule**: If an inventory product is created with a duration of "90 days" or more, the system must calculate a 3% discount on the proportional monthly base price. That is, products with 30-day duration always have the price defined dynamically.

**Required Flows (Separate Use Cases):**
- Flow A: Create a Product (independent).
- Flow B: Add inventory details to an existing Product (using the product ID in the URL).

## Use Cases
**PRODUCT**
CreateProduct
UpdateProduct
DeleteProduct
GetProductById
ListProductsByCategory
**INVENTORY**
CreateInventory (o AddProductToInventory)
UpdateInventoryPrice
UpdateInventoryStock
DeleteInventory
GetInventoryByProduct
DecreaseStock
IncreaseStock

Aggregates (DDD):
Product and Inventory must be separate Aggregate Roots.

Do NOT use bidirectional relationships.
Inventory must reference Product only by productId.
Domain must not depend on JPA entities.

Requirements:
- Use @Version for optimistic locking in InventoryEntity
- Use getReferenceById for product validation optimization
- Return proper HTTP status codes
- No comments in the code
- Clean package structure

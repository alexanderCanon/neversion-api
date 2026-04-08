-- =============================================================
-- V1 __ init_unified_schema.sql
-- Neversion – Unified Schema (Sprint 1.5)
--
-- Strategy (dual-profile idempotency):
--   DEV  : Container starts empty → CREATE TABLE IF NOT EXISTS builds
--          all tables from scratch, then ALTER ADD COLUMN IF NOT EXISTS
--          is a no-op (column already exists inside the new table).
--   PROD : Tables already exist in Supabase → CREATE TABLE IF NOT EXISTS
--          is a no-op, then ALTER ADD COLUMN IF NOT EXISTS safely appends
--          the uuid column without touching existing data.
-- =============================================================

-- ---------------------------------------------------------------
-- EXTENSIONS
-- ---------------------------------------------------------------
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ---------------------------------------------------------------
-- 1. services
--    Core catalog of digital platforms offered (Netflix, Spotify…).
--    'details' JSONB stores inventory-like metadata (pricing tiers,
--    duration options, etc.) without a separate table.
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS services (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    max_profiles INT NOT NULL DEFAULT 5,
    details     JSONB,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE services
    ADD COLUMN IF NOT EXISTS uuid UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX IF NOT EXISTS idx_services_uuid ON services (uuid);


-- ---------------------------------------------------------------
-- 2. accounts
--    Master credentials purchased from wholesalers.
--    Linked to a service; sold either by_profile or full.
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS accounts (
    id           SERIAL PRIMARY KEY,
    service_id   INT NOT NULL REFERENCES services (id),
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    renewal_date DATE NOT NULL,
    plan         VARCHAR(100),
    sale_mode    VARCHAR(20)  NOT NULL DEFAULT 'by_profile',
    notes        TEXT,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE accounts
    ADD COLUMN IF NOT EXISTS uuid UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX IF NOT EXISTS idx_accounts_uuid ON accounts (uuid);
CREATE INDEX IF NOT EXISTS idx_accounts_service    ON accounts (service_id);
CREATE INDEX IF NOT EXISTS idx_accounts_renewal    ON accounts (renewal_date);


-- ---------------------------------------------------------------
-- 3. profiles
--    Physical sub-divisions of an Account (formerly "profiles").
--    is_owner = true → admin profile inside the streaming platform.
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS profiles (
    id         SERIAL PRIMARY KEY,
    account_id INT NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    name       VARCHAR(100) NOT NULL,
    pin        VARCHAR(20),
    is_owner   BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE profiles
    ADD COLUMN IF NOT EXISTS uuid UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX IF NOT EXISTS idx_profiles_uuid    ON profiles (uuid);
CREATE INDEX IF NOT EXISTS idx_profiles_account_id     ON profiles (account_id);


-- ---------------------------------------------------------------
-- 4. clients
--    End consumers (formerly "users_guests").
--    phone is the primary contact channel (WhatsApp automations).
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS clients (
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    phone      VARCHAR(30),
    email      VARCHAR(255),
    notes      TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE clients
    ADD COLUMN IF NOT EXISTS uuid UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX IF NOT EXISTS idx_clients_uuid ON clients (uuid);


-- ---------------------------------------------------------------
-- 5. subscriptions
--    Active link between a Client and a specific Profile.
--    payment_due_date is polled by background automations (n8n).
--    status values: active | suspended | cancelled
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS subscriptions (
    id               SERIAL PRIMARY KEY,
    client_id        INT NOT NULL REFERENCES clients (id),
    profile_id       INT NOT NULL REFERENCES profiles (id),
    start_date       DATE NOT NULL DEFAULT CURRENT_DATE,
    payment_due_date DATE NOT NULL,
    months_paid      INT NOT NULL DEFAULT 1,
    status           VARCHAR(20) NOT NULL DEFAULT 'active',
    notes            TEXT,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

ALTER TABLE subscriptions
    ADD COLUMN IF NOT EXISTS uuid UUID NOT NULL DEFAULT gen_random_uuid();

CREATE UNIQUE INDEX IF NOT EXISTS idx_subscriptions_uuid       ON subscriptions (uuid);
CREATE INDEX IF NOT EXISTS idx_subscriptions_client            ON subscriptions (client_id);
CREATE INDEX IF NOT EXISTS idx_subscriptions_profile           ON subscriptions (profile_id);
CREATE INDEX IF NOT EXISTS idx_subscriptions_payment_due_date  ON subscriptions (payment_due_date);


-- ---------------------------------------------------------------
-- 6. reservations  (storefront bridging – no JPA module yet)
--    Temporary checkout cart created when a client checks out.
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS reservations (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id       INT REFERENCES clients (id),
    discount        NUMERIC(10, 2),
    total           NUMERIC(10, 2),
    receipt_url     TEXT,
    expiration_date TIMESTAMPTZ,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_reservations_client ON reservations (client_id);


-- ---------------------------------------------------------------
-- 7. orders  (storefront bridging – no JPA module yet)
--    Created upon Admin manual validation of a Reservation receipt.
--    status values: PENDING | COMPLETED | REJECTED | CANCELLED
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS orders (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reservation_id UUID REFERENCES reservations (id),
    status         VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    notes          TEXT,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_orders_reservation ON orders (reservation_id);

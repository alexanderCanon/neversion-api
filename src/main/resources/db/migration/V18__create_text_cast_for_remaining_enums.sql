-- The JDBC driver may bind String parameters as 'text' type instead of 'varchar'.
-- This cast ensures PostgreSQL can implicitly convert text → enum for comparisons.
-- V9 only created varchar → enum casts; V16 added text → slot_status.
-- This migration covers all remaining enum types.

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'sub_status')
    THEN CREATE CAST (text AS sub_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'category_type')
    THEN CREATE CAST (text AS category_type) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'account_type')
    THEN CREATE CAST (text AS account_type) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'reserv_status')
    THEN CREATE CAST (text AS reserv_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'order_status')
    THEN CREATE CAST (text AS order_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'account_status')
    THEN CREATE CAST (text AS account_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

-- The JDBC driver may bind String parameters as 'text' type instead of 'varchar'.
-- This cast ensures PostgreSQL can implicitly convert text → slot_status for comparisons.
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'text' AND t.typname = 'slot_status')
    THEN CREATE CAST (text AS slot_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'slot_status')
    THEN CREATE CAST (varchar AS slot_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

-- Usamos bloques anónimos para verificar existencia antes de crear
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'category_type') 
    THEN CREATE CAST (varchar AS category_type) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'account_type') 
    THEN CREATE CAST (varchar AS account_type) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'sub_status') 
    THEN CREATE CAST (varchar AS sub_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'reserv_status') 
    THEN CREATE CAST (varchar AS reserv_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'order_status') 
    THEN CREATE CAST (varchar AS order_status) WITH INOUT AS IMPLICIT; END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_cast c JOIN pg_type s ON c.castsource = s.oid JOIN pg_type t ON c.casttarget = t.oid WHERE s.typname = 'varchar' AND t.typname = 'account_status') 
    THEN CREATE CAST (varchar AS account_status) WITH INOUT AS IMPLICIT; END IF;
END $$;


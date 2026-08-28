-- ---------------------------------------------------------------
-- V43 — Create supabase_functions schema for Database Webhooks
-- Required by Supabase Dashboard to register and manage webhooks.
-- ---------------------------------------------------------------

CREATE SCHEMA IF NOT EXISTS supabase_functions;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'supabase_admin') THEN
        GRANT USAGE, CREATE ON SCHEMA supabase_functions TO postgres, supabase_admin;
        GRANT ALL ON ALL TABLES IN SCHEMA supabase_functions TO postgres, supabase_admin;
        GRANT ALL ON ALL ROUTINES IN SCHEMA supabase_functions TO postgres, supabase_admin;
        GRANT ALL ON ALL SEQUENCES IN SCHEMA supabase_functions TO postgres, supabase_admin;
    ELSE
        GRANT USAGE, CREATE ON SCHEMA supabase_functions TO CURRENT_USER;
    END IF;
END $$;

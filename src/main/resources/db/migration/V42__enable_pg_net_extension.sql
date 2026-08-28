-- ---------------------------------------------------------------
-- V42 — Enable pg_net extension for asynchronous HTTP requests
-- Enables pg_net if available in the PostgreSQL environment (Supabase).
-- ---------------------------------------------------------------

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_available_extensions WHERE name = 'pg_net'
    ) THEN
        CREATE EXTENSION IF NOT EXISTS pg_net WITH SCHEMA extensions;
    END IF;
END $$;

-- ---------------------------------------------------------------
-- V44 — Create supabase_functions.http_request function
-- Official Supabase webhook trigger function using pg_net.
-- ---------------------------------------------------------------

CREATE OR REPLACE FUNCTION supabase_functions.http_request()
RETURNS trigger
LANGUAGE plpgsql
SECURITY DEFINER
SET search_path = extensions, supabase_functions, pg_temp
AS $$
DECLARE
  request_id bigint;
  payload jsonb;
  url text := TG_ARGV[0];
  method text := COALESCE(TG_ARGV[1], 'POST');
  headers jsonb := COALESCE(TG_ARGV[2]::jsonb, '{}'::jsonb);
  params jsonb := COALESCE(TG_ARGV[3]::jsonb, '{}'::jsonb);
  timeout_ms integer := COALESCE(TG_ARGV[4]::integer, 5000);
BEGIN
  IF url IS NULL OR url = '' THEN
    RAISE EXCEPTION 'url is required';
  END IF;

  IF TG_OP = 'DELETE' THEN
    payload := jsonb_build_object(
      'type', TG_OP,
      'table', TG_TABLE_NAME,
      'schema', TG_TABLE_SCHEMA,
      'record', NULL,
      'old_record', row_to_json(OLD)
    );
  ELSIF TG_OP = 'UPDATE' THEN
    payload := jsonb_build_object(
      'type', TG_OP,
      'table', TG_TABLE_NAME,
      'schema', TG_TABLE_SCHEMA,
      'record', row_to_json(NEW),
      'old_record', row_to_json(OLD)
    );
  ELSE
    payload := jsonb_build_object(
      'type', TG_OP,
      'table', TG_TABLE_NAME,
      'schema', TG_TABLE_SCHEMA,
      'record', row_to_json(NEW),
      'old_record', NULL
    );
  END IF;

  -- Only attempt dispatch if pg_net extension is available
  IF EXISTS (SELECT 1 FROM pg_extension WHERE extname = 'pg_net') THEN
    BEGIN
      SELECT net.http_post(
        url := url,
        headers := headers,
        body := payload,
        timeout_milliseconds := timeout_ms
      ) INTO request_id;
    EXCEPTION WHEN OTHERS THEN
      RAISE WARNING 'Failed to dispatch webhook via net.http_post: %', SQLERRM;
    END;
  END IF;

  RETURN NEW;
END;
$$;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'authenticated') THEN
        GRANT EXECUTE ON FUNCTION supabase_functions.http_request() TO postgres, anon, authenticated, service_role;
    ELSE
        GRANT EXECUTE ON FUNCTION supabase_functions.http_request() TO CURRENT_USER;
    END IF;
END $$;

-- user guest
CREATE TABLE users_guests (
  id UUID DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  email TEXT NOT NULL,
  phone TEXT NOT NULL,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id),

  CONSTRAINT unique_user_email UNIQUE(email)
);

CREATE INDEX idx_users_guests_email ON users_guests(email);
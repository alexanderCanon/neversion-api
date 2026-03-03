CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- products
CREATE TABLE products (
  id UUID DEFAULT gen_random_uuid(),
  name VARCHAR(100) NOT NULL,
  description TEXT,
  image_url TEXT,
  category category_type NOT NULL,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id)
);
CREATE INDEX idx_products_active ON products(is_active);
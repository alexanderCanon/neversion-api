-- inventory
CREATE TABLE inventory (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
  account_type account_type NOT NULL,
  duration_days INT NOT NULL CHECK (duration_days IN (30,60,90)),
  price NUMERIC(10,2) NOT NULL CHECK (price > 0),
  stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

  CONSTRAINT unique_variant UNIQUE (product_id, account_type, duration_days)
);

CREATE INDEX idx_inventory_product ON inventory(product_id);
CREATE INDEX idx_inventory_active ON inventory(is_active);
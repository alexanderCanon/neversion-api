-- accounts
CREATE TABLE accounts (
  id UUID DEFAULT gen_random_uuid(),
  product_id UUID NOT NULL REFERENCES products(id),
  email VARCHAR(255) NOT NULL,
  pass VARCHAR(255) NOT NULL,
  seller TEXT NOT NULL,
  price_seller NUMERIC(10,2) NOT NULL CHECK (price_seller > 0),
  account_type account_type NOT NULL,
  expiration_date DATE NOT NULL CHECK (expiration_date > CURRENT_DATE),
  status account_status NOT NULL DEFAULT 'available',
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY (id),

  CONSTRAINT unique_account_email UNIQUE(email)
);

CREATE INDEX idx_accounts_product ON accounts(product_id);
CREATE INDEX idx_accounts_expiration ON accounts(expiration_date);
CREATE INDEX idx_accounts_active ON accounts(is_active);
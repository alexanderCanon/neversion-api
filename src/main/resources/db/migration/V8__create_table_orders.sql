-- orders
CREATE TABLE orders (
  id UUID DEFAULT gen_random_uuid(),
  reservation_id UUID UNIQUE NOT NULL REFERENCES reservations(id),
  user_guest_id UUID REFERENCES users_guests(id),
  discount numeric(10,2) DEFAULT 0,
  total NUMERIC(10,2) NOT NULL CHECK (total > 0),
  status order_status NOT NULL DEFAULT 'validated',
  proof_url TEXT,
  notes TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id)
);

CREATE INDEX idx_orders_created_at ON orders(created_at);

-- order details
CREATE TABLE order_details (
  id UUID DEFAULT gen_random_uuid(),
  order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  inventory_id BIGINT NOT NULL REFERENCES inventory(id),
  qty INT NOT NULL DEFAULT 1 CHECK (qty > 0),
  unit_price NUMERIC(10,2) NOT NULL CHECK (unit_price > 0),
  subtotal NUMERIC(10,2) GENERATED ALWAYS AS (qty * unit_price) STORED,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id)
);

CREATE INDEX idx_order_details_order ON order_details(order_id);

-- CONSTRAINT owner_xor_order CHECK (
--     (profile_id IS NOT NULL AND user_guest_id IS NULL)
--     OR
--     (profile_id IS NULL AND user_guest_id IS NOT NULL)
--   );
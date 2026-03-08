-- reservations
CREATE TABLE reservations (
  id UUID DEFAULT gen_random_uuid(),
  inventory_id BIGINT NOT NULL REFERENCES inventory(id),
  user_guest_id UUID NOT NULL REFERENCES users_guests(id),
  discount numeric(10,2) DEFAULT 0,
  qty INT NOT NULL DEFAULT 1 CHECK (qty > 0),
  status reserv_status NOT NULL DEFAULT 'pending',
  proof_url TEXT UNIQUE,
  expiration_date TIMESTAMPTZ NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id),

  CONSTRAINT expiration_future CHECK (expiration_date > created_at)
);

CREATE INDEX idx_reserv_status ON reservations(status);
CREATE INDEX idx_reservs_expiration ON reservations(expiration_date);

-- reservation details
CREATE TABLE reservation_details (
  id UUID DEFAULT gen_random_uuid(),
  reservation_id UUID NOT NULL REFERENCES reservations(id) ON DELETE CASCADE,
  inventory_id BIGINT NOT NULL REFERENCES inventory(id),
  qty INT NOT NULL DEFAULT 1 CHECK (qty > 0), -- Corregido qty
  unit_price NUMERIC(10,2) NOT NULL CHECK (unit_price > 0), -- Congelamos el precio por 1 hora
  PRIMARY KEY(id)
);
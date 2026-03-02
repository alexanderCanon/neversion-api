```sql
CREATE TYPE category_type AS ENUM (
  'streaming',
  'software',
  'giftcard',
  'recharge',
  'suscrip4u' 
);

CREATE TYPE account_type AS ENUM (
  'familiar',
  'individual'
);

CREATE TYPE sub_status AS ENUM (
  'active',
  'expired',
  'cancelled',
  'suspended'
);

CREATE TYPE reserv_status AS ENUM (
  'pending',
  'uploaded',
  'validated',
  'active',
  'expired',
  'cancelled'
);

CREATE TYPE order_status AS ENUM (
  'validated',
  'completed',
  'rejected',
  'cancelled',
  'pending'
);

CREATE TYPE account_status AS ENUM (
  'available',
  'assigned',
  'expired'
);

-- crypto
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

-- subscriptions
CREATE TABLE subscriptions (
  id UUID DEFAULT gen_random_uuid(),
  profiles_id UUID REFERENCES public.profiles(id),
  user_guest_id UUID REFERENCES users_guests(id),
  account_id UUID NOT NULL REFERENCES accounts(id),
  purchase_date DATE NOT NULL,
  renewal_date DATE NOT NULL,
  profile VARCHAR(20),
  pin VARCHAR(6),
  status sub_status NOT NULL DEFAULT 'active',
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

  CONSTRAINT owner_xor CHECK (
    (profiles_id IS NOT NULL AND user_guest_id IS NULL)
    OR
    (profiles_id IS NULL AND user_guest_id IS NOT NULL)
  ),

  CONSTRAINT renewal_after_purchase CHECK (
    renewal_date > purchase_date
  ),
  PRIMARY KEY (id)
);

-- An individual account can't be applied to two users
CREATE UNIQUE INDEX unique_active_individual_account
ON subscriptions(account_id)
WHERE status = 'active'
AND account_id IN (
    SELECT id FROM accounts WHERE account_type = 'individual'
);

-- reservations
CREATE TABLE reservations (
  id UUID DEFAULT gen_random_uuid(),
  inventory_id BIGINT NOT NULL REFERENCES inventory(id),
  user_guest_id UUID NOT NULL REFERENCES users_guests(id),
  profile_id UUID REFERENCES public.profiles(id),
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

CREATE TABLE reservation_details (
  id UUID DEFAULT gen_random_uuid(),
  reservation_id UUID NOT NULL REFERENCES reservations(id) ON DELETE CASCADE,
  inventory_id BIGINT NOT NULL REFERENCES inventory(id),
  qty INT NOT NULL DEFAULT 1 CHECK (qty > 0), -- Corregido qty
  unit_price NUMERIC(10,2) NOT NULL CHECK (unit_price > 0), -- Congelamos el precio por 1 hora
  PRIMARY KEY(id)
);

-- orders
CREATE TABLE orders (
  id UUID DEFAULT gen_random_uuid(),
  reservation_id UUID UNIQUE NOT NULL REFERENCES reservations(id),
  profile_id UUID REFERENCES public.profiles(id),
  user_guest_id UUID REFERENCES users_guests(id),
  discount numeric(10,2) DEFAULT 0,
  total NUMERIC(10,2) NOT NULL CHECK (total > 0),
  status order_status NOT NULL DEFAULT 'validated',
  proof_url TEXT,
  notes TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY(id),

  CONSTRAINT owner_xor_order CHECK (
    (profile_id IS NOT NULL AND user_guest_id IS NULL)
    OR
    (profile_id IS NULL AND user_guest_id IS NOT NULL)
  )
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

```
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
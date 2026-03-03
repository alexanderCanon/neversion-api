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
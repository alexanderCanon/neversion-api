alter table inventory add column max_profiles INT;
alter table accounts drop column max_profiles;

CREATE TYPE slot_status AS ENUM ('available', 'occupied', 'blocked');

CREATE TABLE account_slots (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    profile_name VARCHAR(100),
    pin VARCHAR(20),
    status slot_status DEFAULT 'available'
);

create index idx_account_slots_account_id on account_slots(account_id);
create index idx_account_slots_status on account_slots(status);

alter table subscriptions add column account_slot_id UUID REFERENCES account_slots(id);
alter table subscriptions add column order_id UUID REFERENCES orders(id);



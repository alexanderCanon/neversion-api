drop table if exists order_details;

alter table reservations drop column inventory_id;
alter table reservations drop column qty;
alter table reservations add column total NUMERIC(10,2) NOT NULL CHECK (total > 0);
alter table reservations rename column proof_url to receipt_url;
alter table reservation_details add column subtotal NUMERIC(10,2) GENERATED ALWAYS AS (qty * unit_price) STORED;
alter table orders drop column user_guest_id,
drop column discount,
drop column total,
drop column proof_url;
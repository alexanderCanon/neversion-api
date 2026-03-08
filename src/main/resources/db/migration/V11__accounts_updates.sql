alter table accounts add column max_profiles INT NOT NULL DEFAULT 1;
alter table accounts drop column product_id;
alter table accounts add column inventory_id BIGINT NOT NULL REFERENCES inventory(id);
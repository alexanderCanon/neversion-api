-- Make user_guest_id nullable (reservation can start without a guest assigned)
ALTER TABLE reservations ALTER COLUMN user_guest_id DROP NOT NULL;

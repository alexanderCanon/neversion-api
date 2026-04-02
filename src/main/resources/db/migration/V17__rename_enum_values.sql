-- Rename enum values to match docs/enums.md standard
ALTER TYPE category_type RENAME VALUE 'giftcard' TO 'gift_card';
ALTER TYPE category_type RENAME VALUE 'suscrip4u' TO 'digital_service';
ALTER TYPE account_type RENAME VALUE 'familiar' TO 'family';

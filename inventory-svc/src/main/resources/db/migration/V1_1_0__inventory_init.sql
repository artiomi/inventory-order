CREATE TABLE IF NOT EXISTS "inventory_items" (
    "id" varchar(20) NOT NULL PRIMARY KEY,
    "name" varchar(20) NOT NULL,
    "available_count" bigint NOT NULL
 )
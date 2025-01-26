CREATE TABLE IF NOT EXISTS "order_items" (
    "id" varchar(50) NOT NULL PRIMARY KEY,
    "name" varchar(20) NOT NULL,
    "inventory_ref" varchar(20) NOT NULL,
    "inventory_count" bigint NOT NULL
 )
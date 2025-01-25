package io.artiomi.inventory.svc.port.out.db;

import io.artiomi.inventory.svc.domain.model.InventoryItem;

public interface InventoryItemDbPort {
    InventoryItem save(InventoryItem item);
}

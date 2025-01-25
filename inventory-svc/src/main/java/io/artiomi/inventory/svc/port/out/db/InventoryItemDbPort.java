package io.artiomi.inventory.svc.port.out.db;

import io.artiomi.inventory.svc.domain.model.InventoryItem;

import java.util.List;

public interface InventoryItemDbPort {
    InventoryItem save(InventoryItem item);

    List<InventoryItem> list();
}

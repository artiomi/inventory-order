package io.artiomi.inventory.svc.port.out.db;

import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.domain.model.InventoryItemQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryItemDbPort {
    InventoryItem save(InventoryItem item);

    List<InventoryItem> search(InventoryItemQuery query);

    void delete(String id);

    Optional<InventoryItem> findById(String id);
}

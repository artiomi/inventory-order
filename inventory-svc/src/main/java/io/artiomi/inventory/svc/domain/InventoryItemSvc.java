package io.artiomi.inventory.svc.domain;

import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.port.out.db.InventoryItemDbPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemSvc {
    private final InventoryItemDbPort inventoryItemDbPort;

    public InventoryItemSvc(InventoryItemDbPort inventoryItemDbPort) {
        this.inventoryItemDbPort = inventoryItemDbPort;
    }

    public void save(InventoryItem item) {
        inventoryItemDbPort.save(item);
    }

    public List<InventoryItem> list() {
        return inventoryItemDbPort.list();
    }
}

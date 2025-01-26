package io.artiomi.inventory.svc.domain;

import io.artiomi.inventory.svc.domain.exception.InventoryException;
import io.artiomi.inventory.svc.domain.model.AcquireRequest;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.domain.model.InventoryItemQuery;
import io.artiomi.inventory.svc.port.out.db.InventoryItemDbPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InventoryItemSvc {
    private final InventoryItemDbPort inventoryItemDbPort;
    private final String acquireWarnPrefix;

    public InventoryItemSvc(
            InventoryItemDbPort inventoryItemDbPort,
            @Value("${app.acquire.warn}") String acquireWarnPrefix) {
        this.inventoryItemDbPort = inventoryItemDbPort;
        this.acquireWarnPrefix = acquireWarnPrefix;
    }

    public InventoryItem save(InventoryItem item) {
        return inventoryItemDbPort.save(item);
    }

    public List<InventoryItem> search(InventoryItemQuery query) {
        return inventoryItemDbPort.search(query);
    }

    public void delete(String id) {
        inventoryItemDbPort.delete(id);
    }

    public InventoryItem acquire(String id, AcquireRequest request) {
        InventoryItem inventoryItem = inventoryItemDbPort.findById(id)
                .orElseThrow(() -> InventoryException.notFound(id));
        long newCount = inventoryItem.availableCount() - request.count();
        if (newCount < 0) {
            log.warn("{} acquire failed for ID: [{}], requested: {}, currently available: {}",
                    acquireWarnPrefix, id, request, inventoryItem);
            throw InventoryException.failed("Acquire failed:" + id);
        }
        InventoryItem updatedEntry = inventoryItem.toBuilder().availableCount(newCount).build();
        return inventoryItemDbPort.save(updatedEntry);
    }
}

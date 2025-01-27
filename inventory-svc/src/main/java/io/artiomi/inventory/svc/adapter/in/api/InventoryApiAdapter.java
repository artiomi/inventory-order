package io.artiomi.inventory.svc.adapter.in.api;

import io.artiomi.inventory.api.contract.InventoryApiResource;
import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.inventory.svc.adapter.InventoryItemApiMapper;
import io.artiomi.inventory.svc.domain.InventoryItemSvc;
import io.artiomi.inventory.svc.domain.model.AcquireRequest;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.domain.model.InventoryItemQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class InventoryApiAdapter implements InventoryApiResource {
    private final InventoryItemSvc inventoryItemSvc;
    private final InventoryItemApiMapper inventoryItemApiMapper;

    public InventoryApiAdapter(
            InventoryItemSvc inventoryItemSvc,
            InventoryItemApiMapper inventoryItemApiMapper
    ) {
        this.inventoryItemSvc = inventoryItemSvc;
        this.inventoryItemApiMapper = inventoryItemApiMapper;
    }

    @Override
    public ResponseEntity<List<InventoryItemApi>> search(String id, String name) {
        var query = InventoryItemQuery.builder().id(id).name(name).build();
        log.info("Inventory item search request received {}", query);
        List<InventoryItem> inventories = inventoryItemSvc.search(query);

        List<InventoryItemApi> response = inventoryItemApiMapper.toApiEntries(inventories);
        log.info("Inventory item search result {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InventoryItemApi> save(InventoryItemApi item) {
        log.info("Inventory save request received {}", item);

        InventoryItem modelEntry = inventoryItemApiMapper.toModelEntry(item);
        InventoryItem saved = inventoryItemSvc.save(modelEntry);

        InventoryItemApi result = inventoryItemApiMapper.toApiEntry(saved);
        log.info("Inventory save result {}", result);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<InventoryItemApi> acquire(String id, AcquireRequestApi acquireRequest) {
        log.info("Inventory acquire request received. id: [{}] payload {}", id, acquireRequest);

        AcquireRequest request = inventoryItemApiMapper.toModelAcquireRequest(acquireRequest);
        InventoryItem item = inventoryItemSvc.acquire(id, request);

        InventoryItemApi result = inventoryItemApiMapper.toApiEntry(item);
        log.info("Inventory acquire request result {}", result);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        log.info("Inventory delete request received. id: [{}] ", id);

        inventoryItemSvc.delete(id);
        log.info("Inventory delete request complete. id: [{}] ", id);
        return ResponseEntity.noContent().build();
    }
}

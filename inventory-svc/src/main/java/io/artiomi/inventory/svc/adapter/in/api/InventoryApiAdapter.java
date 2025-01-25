package io.artiomi.inventory.svc.adapter.in.api;

import io.artiomi.inventory.api.contract.InventoryApiResource;
import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.inventory.svc.adapter.InventoryItemApiMapper;
import io.artiomi.inventory.svc.domain.InventoryItemSvc;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
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
    public ResponseEntity<List<InventoryItemApi>> list(String id, String name) {
        List<InventoryItem> inventories = inventoryItemSvc.list();

        return ResponseEntity.ok(inventoryItemApiMapper.toApiEntries(inventories));
    }

    @Override
    public ResponseEntity<InventoryItemApi> save(InventoryItemApi item) {
        InventoryItem modelEntry = inventoryItemApiMapper.toModelEntry(item);
        InventoryItem saved = inventoryItemSvc.save(modelEntry);

        return ResponseEntity.ok(inventoryItemApiMapper.toApiEntry(saved));
    }

    @Override
    public ResponseEntity<InventoryItemApi> acquire(String id, AcquireRequestApi acquireRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        //TODO call delete
        return ResponseEntity.noContent().build();
    }
}

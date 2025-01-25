package io.artiomi.inventory.svc.adapter.in;

import io.artiomi.inventory.svc.port.in.InventoryApiResource;
import io.artiomi.inventory.svc.adapter.InventoryItemApiMapper;
import io.artiomi.inventory.svc.domain.InventoryItemSvc;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.port.in.model.InventoryItemApi;
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
    public ResponseEntity<List<InventoryItemApi>> list() {
        List<InventoryItem> list = inventoryItemSvc.list();
        List<InventoryItemApi> items = list.stream()
                .map(this.inventoryItemApiMapper::toApiEntry)
                .toList();
//        log.info("items: {}", list);
        return ResponseEntity.ok(items);
    }
}

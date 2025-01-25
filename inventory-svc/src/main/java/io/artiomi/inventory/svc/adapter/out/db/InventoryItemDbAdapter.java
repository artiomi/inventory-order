package io.artiomi.inventory.svc.adapter.out.db;

import io.artiomi.inventory.svc.adapter.InventoryItemDbMapper;
import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.port.out.db.InventoryItemDbPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class InventoryItemDbAdapter implements InventoryItemDbPort {
    private final InventoryItemRepo inventoryItemRepo;
    private final InventoryItemDbMapper inventoryItemDbMapper;

    public InventoryItemDbAdapter(
            InventoryItemRepo inventoryItemRepo,
            InventoryItemDbMapper inventoryItemDbMapper
    ) {
        this.inventoryItemRepo = inventoryItemRepo;
        this.inventoryItemDbMapper = inventoryItemDbMapper;
    }

    @Override
    public InventoryItem save(InventoryItem item) {
        InventoryItemDb dbEntry = inventoryItemDbMapper.toDbEntry(item);
        InventoryItemDb saved = inventoryItemRepo.save(dbEntry);
        return inventoryItemDbMapper.toModelEntry(saved);
    }

    @Override
    public List<InventoryItem> list() {
        List<InventoryItemDb> all = inventoryItemRepo.findAll();
//        log.info("from db: {}", all);
        return all.stream()
                .map(inventoryItemDbMapper::toModelEntry)
                .toList();
    }
}

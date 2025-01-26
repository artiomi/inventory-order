package io.artiomi.inventory.svc.adapter.out.db;

import io.artiomi.inventory.svc.adapter.InventoryItemDbMapper;
import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.domain.model.InventoryItemQuery;
import io.artiomi.inventory.svc.port.out.db.InventoryItemDbPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    private static Example<InventoryItemDb> toSearchExample(InventoryItemQuery query) {
        InventoryItemDb probe = InventoryItemDb.builder()
                .id(query.getId())
                .name(query.getName())
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("availableCount");
        return Example.of(probe, matcher);
    }

    @Override
    public InventoryItem save(InventoryItem item) {
        InventoryItemDb dbEntry = inventoryItemDbMapper.toDbEntry(item);
        InventoryItemDb saved = inventoryItemRepo.save(dbEntry);

        return inventoryItemDbMapper.toModelEntry(saved);
    }

    @Override
    public List<InventoryItem> search(InventoryItemQuery query) {
        List<InventoryItemDb> inventories = inventoryItemRepo.findAll(toSearchExample(query));

        return inventoryItemDbMapper.toModelEntries(inventories);
    }

    @Override
    public void delete(String id) {
        inventoryItemRepo.deleteById(id);
    }

    @Override
    public Optional<InventoryItem> findById(String id) {
        return inventoryItemRepo.findById(id).map(inventoryItemDbMapper::toModelEntry);
    }
}

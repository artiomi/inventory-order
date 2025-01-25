package io.artiomi.inventory.svc.adapter;

import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryItemDbMapper {

    InventoryItemDb toDbEntry(InventoryItem item);

    InventoryItem toModelEntry(InventoryItemDb item);

    List<InventoryItem> toModelEntries(List<InventoryItemDb> items);
}

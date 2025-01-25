package io.artiomi.inventory.svc.adapter;

import io.artiomi.inventory.svc.domain.model.InventoryItem;
import io.artiomi.inventory.svc.port.in.model.InventoryItemApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryItemApiMapper {

    InventoryItemApi toApiEntry(InventoryItem item);

    InventoryItem toModelEntry(InventoryItemApi item);
}

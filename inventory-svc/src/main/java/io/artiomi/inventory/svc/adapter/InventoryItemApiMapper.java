package io.artiomi.inventory.svc.adapter;

import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryItemApiMapper {

    InventoryItemApi toApiEntry(InventoryItem item);

    List<InventoryItemApi> toApiEntries(List<InventoryItem> items);

    InventoryItem toModelEntry(InventoryItemApi item);
}

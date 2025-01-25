package io.artiomi.order.svc.adapter;

import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.order.svc.domain.model.InventoryValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryValueApiMapper {

    List<InventoryValue> toModelEntries(List<InventoryItemApi> items);

    @Mapping(target = "count", source = "availableCount")
    InventoryValue toModelEntry(InventoryItemApi items);
}

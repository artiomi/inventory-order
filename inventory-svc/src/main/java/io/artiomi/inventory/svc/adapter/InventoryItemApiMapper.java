package io.artiomi.inventory.svc.adapter;

import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.inventory.svc.domain.model.AcquireRequest;
import io.artiomi.inventory.svc.domain.model.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryItemApiMapper {

    InventoryItemApi toApiEntry(InventoryItem item);

    List<InventoryItemApi> toApiEntries(List<InventoryItem> items);

    @Mapping(target = "version", ignore = true)
    InventoryItem toModelEntry(InventoryItemApi item);

    AcquireRequest toModelAcquireRequest(AcquireRequestApi requestApi);
}

package io.artiomi.inventory.svc.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InventoryItemQuery {
    String id;
    String name;
}

package io.artiomi.inventory.svc.port.in.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemApi {
    private String id;
    private String name;
    private long availableCount;
}

package io.artiomi.inventory.svc.adapter.out.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "inventory_items")
public final class InventoryItemDb {
    @Id
    private String identifier;
    private String name;
    private Long availableCount;

}

package io.artiomi.inventory.svc.adapter.out.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "inventory_items")
public final class InventoryItemDb {
    @Id
    private String id;
    private String name;
    @Column(name = "available_count")
    private long availableCount;
    @Version
    private Long version;

}

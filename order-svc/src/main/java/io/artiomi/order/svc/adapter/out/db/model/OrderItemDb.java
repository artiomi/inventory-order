package io.artiomi.order.svc.adapter.out.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_items")
@Builder
public class OrderItemDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(name = "inventory_ref")
    private String inventoryRef;
    @Column(name = "inventory_count")
    private long inventoryCount;
}

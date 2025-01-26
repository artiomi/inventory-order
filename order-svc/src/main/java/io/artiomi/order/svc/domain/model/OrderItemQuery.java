package io.artiomi.order.svc.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItemQuery {
    String id;
    String name;
    String inventoryRef;
}

package io.artiomi.order.svc.domain;

import io.artiomi.order.svc.domain.model.InventoryValue;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.domain.model.OrderItemQuery;
import io.artiomi.order.svc.port.out.api.InventoryApiPort;
import io.artiomi.order.svc.port.out.db.OrderItemDbPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemSvc {
    private final OrderItemDbPort orderItemDbPort;
    private final InventoryApiPort inventoryApiPort;

    public OrderItemSvc(
            OrderItemDbPort orderItemDbPort,
            InventoryApiPort inventoryApiPort
    ) {
        this.orderItemDbPort = orderItemDbPort;
        this.inventoryApiPort = inventoryApiPort;
    }

    public List<OrderItem> search(OrderItemQuery query) {
        return orderItemDbPort.search(query);
    }

    public OrderItem create(OrderItem item) {

        InventoryValue updatedValue = InventoryValue.builder()
                .id(item.inventoryRef())
                .count(item.inventoryCount())
                .build();

        inventoryApiPort.acquire(updatedValue);

        return orderItemDbPort.save(item);
    }
}

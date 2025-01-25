package io.artiomi.order.svc.domain;

import io.artiomi.order.svc.domain.model.InventoryValue;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.port.out.api.InventoryApiPort;
import io.artiomi.order.svc.port.out.db.OrderItemDbPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<OrderItem> list() {
        return orderItemDbPort.list();
    }

    @Transactional(timeout = 5)
    public OrderItem save(OrderItem item) {
        List<InventoryValue> inventories = inventoryApiPort.get(item.inventoryRef());
        if (inventories.size() != 1) {
            throw new RuntimeException("to many items");
        }
        InventoryValue inventoryValue = inventories.getFirst();
        if (inventoryValue.getCount() < item.inventoryCount()) {
            throw new RuntimeException("not enough");
        }
        long newCount = inventoryValue.getCount() - item.inventoryCount();
        InventoryValue updatedValue = inventoryValue.toBuilder().count(newCount).build();
        OrderItem saved = orderItemDbPort.save(item);
        inventoryApiPort.acquire(updatedValue);

        return saved;
    }
}

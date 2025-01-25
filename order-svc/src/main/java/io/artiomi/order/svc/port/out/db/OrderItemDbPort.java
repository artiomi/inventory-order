package io.artiomi.order.svc.port.out.db;

import io.artiomi.order.svc.domain.model.OrderItem;

import java.util.List;

public interface OrderItemDbPort {

    List<OrderItem> list();

    OrderItem save(OrderItem item);
}

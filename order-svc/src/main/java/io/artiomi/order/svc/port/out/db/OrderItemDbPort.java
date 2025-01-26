package io.artiomi.order.svc.port.out.db;

import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.domain.model.OrderItemQuery;

import java.util.List;

public interface OrderItemDbPort {

    List<OrderItem> search(OrderItemQuery query);

    OrderItem save(OrderItem item);
}

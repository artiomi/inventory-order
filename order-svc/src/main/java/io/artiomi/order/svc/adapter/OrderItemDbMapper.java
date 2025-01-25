package io.artiomi.order.svc.adapter;

import io.artiomi.order.svc.adapter.out.db.model.OrderItemDb;
import io.artiomi.order.svc.domain.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemDbMapper {

    OrderItemDb toDbEntry(OrderItem item);

    OrderItem toModelEntry(OrderItemDb item);

    List<OrderItem> toModelEntries(List<OrderItemDb> item);
}

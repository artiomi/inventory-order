package io.artiomi.order.svc.adapter;

import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.svc.domain.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemApiMapper {

    OrderItemApi toApiEntry(OrderItem item);

    OrderItem toModelEntry(OrderItemApi item);

    List<OrderItemApi> toApiEntries(List<OrderItem> item);
}

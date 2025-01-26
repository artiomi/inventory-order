package io.artiomi.order.svc.adapter;

import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import io.artiomi.order.svc.domain.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemApiMapper {

    OrderItemApi toApiEntry(OrderItem item);

    OrderItem toModelEntry(String id, OrderItemUpsertRequest request);

    List<OrderItemApi> toApiEntries(List<OrderItem> item);
}

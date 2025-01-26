package io.artiomi.order.svc.adapter.in.api;

import io.artiomi.order.api.contract.OrderApiResource;
import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import io.artiomi.order.svc.adapter.OrderItemApiMapper;
import io.artiomi.order.svc.domain.OrderItemSvc;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.domain.model.OrderItemQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderApiAdapter implements OrderApiResource {
    private final OrderItemSvc orderItemSvc;
    private final OrderItemApiMapper orderItemApiMapper;

    public OrderApiAdapter(
            OrderItemSvc orderItemSvc,
            OrderItemApiMapper orderItemApiMapper
    ) {
        this.orderItemSvc = orderItemSvc;
        this.orderItemApiMapper = orderItemApiMapper;
    }


    @Override
    public ResponseEntity<List<OrderItemApi>> search(String id, String name, String inventoryRef) {
        OrderItemQuery query = OrderItemQuery.builder().id(id).name(name).inventoryRef(inventoryRef).build();

        List<OrderItem> orders = orderItemSvc.search(query);
        return ResponseEntity.ok(orderItemApiMapper.toApiEntries(orders));
    }

    @Override
    public ResponseEntity<OrderItemApi> update(String id, OrderItemUpsertRequest request) {
        OrderItem modelEntry = orderItemApiMapper.toModelEntry(id, request);
        OrderItem saved = orderItemSvc.save(modelEntry);

        return new ResponseEntity<>(orderItemApiMapper.toApiEntry(saved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderItemApi> create(OrderItemUpsertRequest request) {
        OrderItem modelEntry = orderItemApiMapper.toModelEntry(null, request);
        OrderItem saved = orderItemSvc.save(modelEntry);

        return ResponseEntity.ok(orderItemApiMapper.toApiEntry(saved));
    }
}

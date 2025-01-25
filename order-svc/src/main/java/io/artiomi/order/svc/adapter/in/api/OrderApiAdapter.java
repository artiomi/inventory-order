package io.artiomi.order.svc.adapter.in.api;

import io.artiomi.order.api.contract.OrderApiResource;
import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.svc.adapter.OrderItemApiMapper;
import io.artiomi.order.svc.domain.OrderItemSvc;
import io.artiomi.order.svc.domain.model.OrderItem;
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
    public ResponseEntity<List<OrderItemApi>> list(String id, String name, String inventoryRef) {
        List<OrderItem> orders = orderItemSvc.list();
        return ResponseEntity.ok(orderItemApiMapper.toApiEntries(orders));
    }

    @Override
    public ResponseEntity<OrderItemApi> save(OrderItemApi item) {
        OrderItem modelEntry = orderItemApiMapper.toModelEntry(item);
        OrderItem saved = orderItemSvc.save(modelEntry);

        return ResponseEntity.ok(orderItemApiMapper.toApiEntry(saved));
    }
}

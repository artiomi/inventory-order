package io.artiomi.order.svc.adapter.in.api;

import io.artiomi.order.api.contract.OrderApiResource;
import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import io.artiomi.order.svc.adapter.OrderItemApiMapper;
import io.artiomi.order.svc.domain.OrderItemSvc;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.domain.model.OrderItemQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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
        log.info("Order search request received. {}", query);
        List<OrderItem> orders = orderItemSvc.search(query);
        List<OrderItemApi> result = orderItemApiMapper.toApiEntries(orders);
        log.info("Order search result. {}", result);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<OrderItemApi> update(String id, OrderItemUpsertRequest request) {
        log.info("Order update request received. id:[{}] payload {}", id, request);

        OrderItem modelEntry = orderItemApiMapper.toModelEntry(id, request);
        OrderItem saved = orderItemSvc.save(modelEntry);

        OrderItemApi result = orderItemApiMapper.toApiEntry(saved);
        log.info("Order update request complete. {}", result);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<OrderItemApi> create(OrderItemUpsertRequest request) {
        log.info("Order create request received. {}", request);

        OrderItem modelEntry = orderItemApiMapper.toModelEntry(null, request);
        OrderItem saved = orderItemSvc.save(modelEntry);
        OrderItemApi result = orderItemApiMapper.toApiEntry(saved);
        log.info("Order create request complete. {}", result);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}

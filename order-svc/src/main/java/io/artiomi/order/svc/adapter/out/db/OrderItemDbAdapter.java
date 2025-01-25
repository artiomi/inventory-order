package io.artiomi.order.svc.adapter.out.db;

import io.artiomi.order.svc.adapter.OrderItemDbMapper;
import io.artiomi.order.svc.adapter.out.db.model.OrderItemDb;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.port.out.db.OrderItemDbPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDbAdapter implements OrderItemDbPort {
    private final OrderItemRepo orderItemRepo;
    private final OrderItemDbMapper orderItemDbMapper;

    public OrderItemDbAdapter(
            OrderItemRepo orderItemRepo,
            OrderItemDbMapper orderItemDbMapper
    ) {
        this.orderItemRepo = orderItemRepo;
        this.orderItemDbMapper = orderItemDbMapper;
    }

    @Override
    public List<OrderItem> list() {
        List<OrderItemDb> findResult = orderItemRepo.findAll();
        return orderItemDbMapper.toModelEntries(findResult);
    }

    @Override
    public OrderItem save(OrderItem item) {
        OrderItemDb dbEntry = orderItemDbMapper.toDbEntry(item);
        OrderItemDb saved = orderItemRepo.save(dbEntry);

        return orderItemDbMapper.toModelEntry(saved);
    }
}

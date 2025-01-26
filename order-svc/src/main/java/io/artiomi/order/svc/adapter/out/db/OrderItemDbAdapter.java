package io.artiomi.order.svc.adapter.out.db;

import io.artiomi.order.svc.adapter.OrderItemDbMapper;
import io.artiomi.order.svc.adapter.out.db.model.OrderItemDb;
import io.artiomi.order.svc.domain.model.OrderItem;
import io.artiomi.order.svc.domain.model.OrderItemQuery;
import io.artiomi.order.svc.port.out.db.OrderItemDbPort;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
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

    private static Example<OrderItemDb> toSearchExample(OrderItemQuery query) {
        OrderItemDb probe = OrderItemDb.builder()
                .id(query.getId())
                .name(query.getName())
                .inventoryRef(query.getInventoryRef())
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("inventoryCount");
        return Example.of(probe, matcher);
    }

    @Override
    public List<OrderItem> search(OrderItemQuery query) {
        List<OrderItemDb> findResult = orderItemRepo.findAll(toSearchExample(query), Sort.by("id"));
        return orderItemDbMapper.toModelEntries(findResult);
    }

    @Override
    public OrderItem save(OrderItem item) {
        OrderItemDb dbEntry = orderItemDbMapper.toDbEntry(item);
        OrderItemDb saved = orderItemRepo.save(dbEntry);

        return orderItemDbMapper.toModelEntry(saved);
    }
}

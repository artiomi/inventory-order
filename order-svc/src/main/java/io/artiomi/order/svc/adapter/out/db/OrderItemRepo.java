package io.artiomi.order.svc.adapter.out.db;

import io.artiomi.order.svc.adapter.out.db.model.OrderItemDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItemDb, String> {
}

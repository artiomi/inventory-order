package io.artiomi.order.svc.port.out.api;

import io.artiomi.order.svc.domain.model.InventoryValue;

import java.util.List;

public interface InventoryApiPort {

    List<InventoryValue> get(String id);

    InventoryValue acquire(InventoryValue inventoryValue);
}

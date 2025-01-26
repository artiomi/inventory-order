package io.artiomi.order.svc.port.out.api;

import io.artiomi.order.svc.domain.model.InventoryValue;

public interface InventoryApiPort {

    void acquire(InventoryValue inventoryValue);
}

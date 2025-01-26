package io.artiomi.order.svc.adapter.out.db;

import io.artiomi.inventory.api.client.InventoryApiClient;
import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.order.svc.domain.exception.OrderException;
import io.artiomi.order.svc.domain.model.InventoryValue;
import io.artiomi.order.svc.port.out.api.InventoryApiPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class InventoryApiAdapter implements InventoryApiPort {
    private final InventoryApiClient inventoryApiClient;

    public InventoryApiAdapter(InventoryApiClient inventoryApiClient) {
        this.inventoryApiClient = inventoryApiClient;
    }

    @Override
    public void acquire(InventoryValue inventory) {
        AcquireRequestApi acquireRequest = new AcquireRequestApi(inventory.getCount());
        try {
            inventoryApiClient.acquire(inventory.getId(), acquireRequest);
        } catch (RestClientException e) {
            log.error("Acquire of Inventory item with ID:[{}] and request [{}] failed.",
                    inventory.getId(), acquireRequest, e);
            throw OrderException.failed(String.format("Acquire of Inventory item with ID:[%s] failed. Cause: %s",
                    inventory.getId(), e.getMessage()));
        }

    }
}

package io.artiomi.order.svc.adapter.out.db;

import io.artiomi.inventory.api.client.InventoryApiClient;
import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.order.svc.adapter.InventoryValueApiMapper;
import io.artiomi.order.svc.domain.model.InventoryValue;
import io.artiomi.order.svc.port.out.api.InventoryApiPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Component
public class InventoryApiAdapter implements InventoryApiPort {
    private final InventoryApiClient inventoryApiClient;
    private final InventoryValueApiMapper inventoryValueApiMapper;

    public InventoryApiAdapter(
            InventoryApiClient inventoryApiClient,
            InventoryValueApiMapper inventoryValueApiMapper
    ) {
        this.inventoryApiClient = inventoryApiClient;
        this.inventoryValueApiMapper = inventoryValueApiMapper;
    }

    @Override
    public List<InventoryValue> get(String id) {
        try {
            ResponseEntity<List<InventoryItemApi>> response = inventoryApiClient.list(id, null);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                return inventoryValueApiMapper.toModelEntries(response.getBody());
            } else {
                throw new RuntimeException("implement me");///TODO
            }

        } catch (RestClientException e) {
            log.error("Get item by Id: {} failed.", id, e);
            throw e;//TODO error handling
        }
    }

    @Override
    public InventoryValue acquire(InventoryValue inventoryValue) {
        try {
            AcquireRequestApi acquireRequest = new AcquireRequestApi(inventoryValue.getCount());
            ResponseEntity<InventoryItemApi> response =
                    inventoryApiClient.acquire(inventoryValue.getId(), acquireRequest);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                return inventoryValueApiMapper.toModelEntry(response.getBody());
            } else {
                throw new RuntimeException("implement me");///TODO
            }
        } catch (RestClientException e) {
            log.error("Update of invtoryItem failed: {} .", inventoryValue, e);
            throw e;//TODO error handling
        }

    }
}

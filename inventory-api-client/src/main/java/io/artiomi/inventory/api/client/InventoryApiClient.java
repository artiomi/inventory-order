package io.artiomi.inventory.api.client;

import io.artiomi.inventory.api.contract.InventoryApiResource;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${app.url.inventory}", name = "inventory-client")
public interface InventoryApiClient extends InventoryApiResource {
}

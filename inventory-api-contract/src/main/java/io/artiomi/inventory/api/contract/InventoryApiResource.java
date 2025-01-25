package io.artiomi.inventory.api.contract;

import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface InventoryApiResource {

    @GetMapping("/inventories/")
    ResponseEntity<List<InventoryItemApi>> list(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name);

    @PostMapping("/inventories/")
    ResponseEntity<InventoryItemApi> save(@Valid @RequestBody InventoryItemApi item);

    @PutMapping("/inventories/{id}/acquire/")
    ResponseEntity<InventoryItemApi> acquire(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody AcquireRequestApi acquireRequest);

    @DeleteMapping("/inventories/{id}/")
    ResponseEntity<Void> delete(@PathVariable(name = "id") String id);
}
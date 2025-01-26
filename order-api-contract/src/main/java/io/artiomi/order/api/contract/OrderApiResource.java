package io.artiomi.order.api.contract;

import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrderApiResource {

    @GetMapping("/orders/")
    ResponseEntity<List<OrderItemApi>> search(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "inventoryRef", required = false) String inventoryRef);

    @PutMapping(value = "/orders/{id}")
    ResponseEntity<OrderItemApi> update(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody OrderItemUpsertRequest request);

    @PostMapping(value = "/orders/")
    ResponseEntity<OrderItemApi> update(@Valid @RequestBody OrderItemUpsertRequest request);
}

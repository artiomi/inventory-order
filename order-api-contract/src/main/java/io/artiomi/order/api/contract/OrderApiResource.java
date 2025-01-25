package io.artiomi.order.api.contract;

import io.artiomi.order.api.contract.model.OrderItemApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderApiResource {

    @GetMapping("/orders/")
    ResponseEntity<List<OrderItemApi>> list(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "inventoryRef", required = false) String inventoryRef);

    @PostMapping("/orders/")
    ResponseEntity<OrderItemApi> save(@Valid @RequestBody OrderItemApi item);
}

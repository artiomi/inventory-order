package io.artiomi.inventory.svc.port.in;

import io.artiomi.inventory.svc.port.in.model.InventoryItemApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/inventory")
public interface InventoryApiResource {

    @GetMapping("/")
    ResponseEntity<List<InventoryItemApi>> list();
}

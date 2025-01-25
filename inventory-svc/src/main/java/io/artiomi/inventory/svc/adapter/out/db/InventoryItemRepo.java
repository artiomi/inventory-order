package io.artiomi.inventory.svc.adapter.out.db;

import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import org.springframework.data.repository.ListCrudRepository;

public interface InventoryItemRepo extends ListCrudRepository<InventoryItemDb, String> {

}

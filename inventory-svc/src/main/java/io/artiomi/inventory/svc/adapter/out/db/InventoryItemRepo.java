package io.artiomi.inventory.svc.adapter.out.db;

import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import org.springframework.data.repository.CrudRepository;

public interface InventoryItemRepo extends CrudRepository<InventoryItemDb, String> {

}

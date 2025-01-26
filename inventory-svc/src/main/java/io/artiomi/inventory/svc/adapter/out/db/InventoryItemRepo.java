package io.artiomi.inventory.svc.adapter.out.db;

import io.artiomi.inventory.svc.adapter.out.db.model.InventoryItemDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepo extends JpaRepository<InventoryItemDb, String> {

}

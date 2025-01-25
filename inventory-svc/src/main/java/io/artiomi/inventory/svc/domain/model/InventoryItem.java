package io.artiomi.inventory.svc.domain.model;

public record InventoryItem(String identifier, String name, long availableCount) {
}

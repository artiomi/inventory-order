package io.artiomi.inventory.svc.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record InventoryItem(String id, String name, long availableCount, Long version) {
}

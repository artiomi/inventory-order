package io.artiomi.order.svc.domain.model;

public record OrderItem(String id, String name, String inventoryRef, long inventoryCount) {
}

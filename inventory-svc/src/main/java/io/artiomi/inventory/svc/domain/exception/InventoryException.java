package io.artiomi.inventory.svc.domain.exception;

public class InventoryException extends RuntimeException {

    private static final String MSG_ITEM_NOT_FOUND = "InventoryItem with ID: [%s] not found";
    private final Category category;

    private InventoryException(String message, Category category) {
        super(message);
        this.category = category;
    }

    public static InventoryException notFound(String id) {
        String message = String.format(MSG_ITEM_NOT_FOUND, id);

        return new InventoryException(message, Category.NOT_FOUND);
    }

    public static InventoryException failed(String reason) {
        return new InventoryException(reason, Category.OTHER);
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        NOT_FOUND, OTHER
    }
}

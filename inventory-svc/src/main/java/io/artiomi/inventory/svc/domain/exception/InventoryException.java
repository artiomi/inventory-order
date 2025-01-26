package io.artiomi.inventory.svc.domain.exception;

public class InventoryException extends RuntimeException {

    private static final String MSG_ITEM_NOT_FOUND = "InventoryItem with ID: [%s] not found";

    private InventoryException(String message) {
        super(message);
    }

    public static InventoryException notFound(String id) {
        String message = String.format(MSG_ITEM_NOT_FOUND, id);

        return failed(message);
    }

    public static InventoryException failed(String reason) {

        return new InventoryException(reason);
    }
}

package io.artiomi.order.svc.domain.exception;

public class OrderException extends RuntimeException {
    private OrderException(String message) {
        super(message);
    }

    public static OrderException failed(String message) {
        return new OrderException(message);
    }
}

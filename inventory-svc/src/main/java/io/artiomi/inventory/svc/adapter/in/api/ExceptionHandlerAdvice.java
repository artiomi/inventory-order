package io.artiomi.inventory.svc.adapter.in.api;

import io.artiomi.inventory.api.contract.model.ExceptionResponse;
import io.artiomi.inventory.svc.domain.exception.InventoryException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<ExceptionResponse> handleOrderFailure(InventoryException cause) {
        if (cause.getCategory() == InventoryException.Category.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }
        ExceptionResponse response = ExceptionResponse.builder().cause(cause.getMessage()).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleArgumentValidationFailure(Throwable cause) {
        return ExceptionResponse.builder().cause(cause.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleGenericFailure(Exception cause) {
        return ExceptionResponse.builder().cause(cause.getMessage()).build();
    }
}

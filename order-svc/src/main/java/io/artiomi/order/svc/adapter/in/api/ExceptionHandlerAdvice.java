package io.artiomi.order.svc.adapter.in.api;

import io.artiomi.order.api.contract.model.ExceptionResponse;
import io.artiomi.order.svc.domain.exception.OrderException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(OrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleOrderFailure(OrderException cause) {
        return ExceptionResponse.builder().cause(cause.getMessage()).build();
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

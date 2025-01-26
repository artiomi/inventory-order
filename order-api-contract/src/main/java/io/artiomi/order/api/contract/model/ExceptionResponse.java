package io.artiomi.order.api.contract.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExceptionResponse {
    String cause;

}

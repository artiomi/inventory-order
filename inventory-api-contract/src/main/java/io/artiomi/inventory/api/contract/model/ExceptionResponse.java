package io.artiomi.inventory.api.contract.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExceptionResponse {
    String cause;

}

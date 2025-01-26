package io.artiomi.order.api.contract.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemApi {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String inventoryRef;
    @Min(1)
    private long inventoryCount;
}

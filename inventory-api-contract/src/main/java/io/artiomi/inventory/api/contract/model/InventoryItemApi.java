package io.artiomi.inventory.api.contract.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemApi {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @Min(1)
    private long availableCount;
}

package io.artiomi.inventory.api.contract.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireRequestApi {
    @Min(1)
    private long count;
}

package com.mycompany.warehouse.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record DeliveryAddress(
    @NonNull @NotBlank String name,
    @NonNull @NotBlank String address
) {
}

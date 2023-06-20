package com.mycompany.warehouse.domain;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.util.UUID;

public record StockReleaseOrderItem(
    @NonNull UUID productId,
    @Min(1) int quantity
) {
}

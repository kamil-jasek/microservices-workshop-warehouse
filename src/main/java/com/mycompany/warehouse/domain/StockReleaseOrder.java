package com.mycompany.warehouse.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public record StockReleaseOrder(@NonNull UUID waybillId,
                                @NonNull @Valid DeliveryAddress deliveryAddress,
                                @NonNull @Valid @NotEmpty List<StockReleaseOrderItem> items) {

    public boolean hasProductOutOfStock(UUID productIdOutOfStock) {
        return items()
            .stream()
            .anyMatch(item -> item.productId().equals(productIdOutOfStock));
    }
}

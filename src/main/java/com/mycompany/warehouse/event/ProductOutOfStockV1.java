package com.mycompany.warehouse.event;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

import static com.mycompany.warehouse.event.ProductOutOfStockV1.ProductOutOfStockData;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@JsonSubtype
public final class ProductOutOfStockV1 extends DomainEvent<ProductOutOfStockData> {

    public ProductOutOfStockV1(@NonNull UUID eventId,
                               @NonNull Instant eventTime,
                               UUID correlationId,
                               @NonNull ProductOutOfStockData data) {
        super(eventId, eventTime, correlationId, data);
    }

    public static ProductOutOfStockV1 of(ProductOutOfStockData data) {
        return new ProductOutOfStockV1(
            randomUUID(),
            now(),
            null,
            data);
    }

    public record ProductOutOfStockData(UUID orderId, UUID productId) implements DomainEventData {
    }
}

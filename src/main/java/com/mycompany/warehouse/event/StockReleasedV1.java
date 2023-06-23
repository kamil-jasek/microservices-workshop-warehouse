package com.mycompany.warehouse.event;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

import static com.mycompany.warehouse.event.StockReleasedV1.StockReleased;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@JsonSubtype
public final class StockReleasedV1 extends DomainEvent<StockReleased> {

    public record StockReleased(UUID waybillId, UUID shipmentId) implements DomainEventData {
    }

    public StockReleasedV1(@NonNull UUID eventId,
                           @NonNull Instant eventTime,
                           UUID correlationId,
                           @NonNull StockReleased data) {
        super(eventId, eventTime, correlationId, data);
    }

    public static StockReleasedV1 of(StockReleased data) {
        return new StockReleasedV1(
            randomUUID(),
            now(),
            null,
            data);
    }
}

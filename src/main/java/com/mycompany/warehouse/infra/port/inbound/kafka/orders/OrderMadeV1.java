package com.mycompany.warehouse.infra.port.inbound.kafka.orders;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.mycompany.warehouse.infra.port.inbound.kafka.orders.OrderMadeV1.OrderMadeData;

@JsonSubtype
final class OrderMadeV1 extends DomainEvent<OrderMadeData> {

    public OrderMadeV1(@NonNull UUID eventId,
                       @NonNull Instant eventTime,
                       UUID correlationId,
                       @NonNull OrderMadeData data) {
        super(eventId, eventTime, correlationId, data);
    }

    record OrderMadeData(
        @NonNull UUID id,
        @NonNull UUID customerId,
        @NonNull Instant createTime,
        @NonNull String status,
        @NonNull String currency,
        @NonNull List<OrderMadeItem> orderItems,
        @NonNull BigDecimal discount,
        @NonNull BigDecimal deliveryCost
    ) implements DomainEventData {

        record OrderMadeItem(
            @NonNull UUID productId,
            @NonNull BigDecimal originalPrice,
            @NonNull String originalCurrency,
            @NonNull BigDecimal exchangedPrice,
            double weight,
            @NonNull String weightUnit,
            int quantity
        ) {
        }
    }
}

package com.mycompany.application.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static com.mycompany.application.event.DomainEvent.DomainEventData;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonTypeInfo(use = NAME, property = "name")
public abstract class DomainEvent<T extends DomainEventData> {
    private final @NonNull UUID eventId;
    private final @NonNull Instant eventTime;
    private final UUID correlationId;
    private final @NonNull T data;

    public interface DomainEventData extends Serializable {
    }
}

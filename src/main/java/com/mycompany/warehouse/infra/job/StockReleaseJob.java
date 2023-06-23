package com.mycompany.warehouse.infra.job;

import com.mycompany.warehouse.StockReleaseQueue;
import com.mycompany.warehouse.domain.StockReleaseOrder;
import com.mycompany.warehouse.event.ProductOutOfStockV1;
import com.mycompany.warehouse.event.ProductOutOfStockV1.ProductOutOfStockData;
import com.mycompany.warehouse.event.StockReleasedV1;
import com.mycompany.warehouse.event.StockReleasedV1.StockReleased;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.concurrent.TimeUnit.MINUTES;

@RequiredArgsConstructor
class StockReleaseJob {

    private final @NonNull StockReleaseQueue queue;
    private final @NonNull KafkaTemplate<String, Object> kafkaTemplate;
    private final UUID productIdOutOfStock = UUID.fromString("3b1ad028-d67b-4ea4-a7eb-c51b268b277e");

    @SneakyThrows
    @Scheduled(fixedRate = 1, timeUnit = MINUTES)
    @Transactional
    void releaseStock() {
        final var order = queue.poll();
        if (order == null) {
            return;
        }
        if (order.hasProductOutOfStock(productIdOutOfStock)) {
            publishProductOutOfStockEvent(order.waybillId());
        } else {
            publishStockReleased(order);
        }
    }

    private void publishProductOutOfStockEvent(@NonNull UUID orderId) {
        kafkaTemplate.send(
            "warehouse-product-out-of-stock-v1",
            ProductOutOfStockV1.of(new ProductOutOfStockData(orderId, productIdOutOfStock)));
    }

    private void publishStockReleased(StockReleaseOrder order) {
        kafkaTemplate.send(
            "warehouse-stock-released-v1",
            StockReleasedV1.of(new StockReleased(order.waybillId(), UUID.randomUUID())));
    }
}

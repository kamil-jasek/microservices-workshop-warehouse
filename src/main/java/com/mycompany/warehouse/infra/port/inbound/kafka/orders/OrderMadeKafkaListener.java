package com.mycompany.warehouse.infra.port.inbound.kafka.orders;

import com.mycompany.warehouse.StockReleaseQueue;
import com.mycompany.warehouse.domain.StockReleaseOrder;
import com.mycompany.warehouse.domain.StockReleaseOrderItem;
import com.mycompany.warehouse.infra.port.inbound.kafka.orders.OrderMadeV1.OrderMadeData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
class OrderMadeKafkaListener {

    private final @NonNull StockReleaseQueue stockReleaseQueue;
    private final @NonNull CustomerRestApi customerRestApi;

    @SneakyThrows
    @KafkaListener(
        id = "warehouse-order-made-listener",
        topics = {
            "orders-order-made-v1"
        },
        containerFactory = "domainEventKafkaContainerFactory"
    )
    @Transactional
    void handle(OrderMadeV1 event) {
        log.info("processing event: " + event);
        stockReleaseQueue.add(createStockReleaseOrder(event.getData()));
    }

    private StockReleaseOrder createStockReleaseOrder(OrderMadeData order) {
        return new StockReleaseOrder(
            order.id(),
            customerRestApi.findById(order.customerId()).deliveryAddress(),
            order.orderItems()
                .stream()
                .map(item -> new StockReleaseOrderItem(item.productId(), item.quantity()))
                .collect(toList())
        );
    }
}

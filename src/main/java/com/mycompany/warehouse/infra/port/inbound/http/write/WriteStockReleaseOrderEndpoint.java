package com.mycompany.warehouse.infra.port.inbound.http.write;

import com.mycompany.warehouse.StockReleaseQueue;
import com.mycompany.warehouse.domain.StockReleaseOrder;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stock-release-orders")
@RequiredArgsConstructor
@Profile("processmanager")
final class WriteStockReleaseOrderEndpoint {

    private final @NonNull StockReleaseQueue stockReleaseQueue;

    @PostMapping
    void createStockReleaseOrder(@RequestBody @Valid StockReleaseOrder stockReleaseOrder) {
        stockReleaseQueue.add(stockReleaseOrder);
    }
}

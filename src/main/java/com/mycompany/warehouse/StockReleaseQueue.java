package com.mycompany.warehouse;

import com.mycompany.warehouse.domain.StockReleaseOrder;
import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class StockReleaseQueue {

    private final BlockingQueue<StockReleaseOrder> orders = new LinkedBlockingQueue<>();

    public void add(StockReleaseOrder order) {
        orders.add(order);
    }

    @SneakyThrows
    public StockReleaseOrder poll() {
        return orders.poll();
    }
}

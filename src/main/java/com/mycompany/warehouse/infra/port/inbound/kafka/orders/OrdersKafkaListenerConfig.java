package com.mycompany.warehouse.infra.port.inbound.kafka.orders;

import com.mycompany.warehouse.StockReleaseQueue;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableFeignClients(clients = CustomerRestApi.class)
class OrdersKafkaListenerConfig {

    @Bean
    @Profile("!processmanager")
    OrderMadeKafkaListener orderMadeKafkaListener(StockReleaseQueue queue,
                                                  CustomerRestApi customerRestApi) {
        return new OrderMadeKafkaListener(queue, customerRestApi);
    }
}

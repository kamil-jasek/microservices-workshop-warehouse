package com.mycompany.warehouse.infra.port.inbound.kafka.orders;

import com.mycompany.warehouse.domain.DeliveryAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customers", configuration = CustomerRestApiConfig.class)
interface CustomerRestApi {

    record CustomerDto(UUID id, DeliveryAddress deliveryAddress) {
    }

    @GetMapping(value = "/api/customers/{id}", headers = {"version=1.0.0"})
    CustomerDto findById(@PathVariable UUID id);
}

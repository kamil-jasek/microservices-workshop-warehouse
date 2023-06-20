package com.mycompany.warehouse.infra.port.inbound.kafka.orders;

import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

class CustomerRestApiConfig {

    @Bean
    Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    ErrorDecoder errorDecoder() {
        final var defaultDecoder = new ErrorDecoder.Default();
        return (methodKey, response) -> {
            if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
                throw new RetryableException(
                    response.status(),
                    "retry on 5xx error",
                    response.request().httpMethod(),
                    null,
                    response.request()
                );
            }
            return defaultDecoder.decode(methodKey, response);
        };
    }
}

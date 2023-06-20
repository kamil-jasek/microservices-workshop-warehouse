package com.mycompany.warehouse.infra.job;

import com.mycompany.warehouse.StockReleaseQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
class StockReleaseJobConfig {

    @Bean
    StockReleaseJob stockReleaseJob(KafkaTemplate<String, Object> kafkaTemplate,
                                    StockReleaseQueue queue) {
        return new StockReleaseJob(queue, kafkaTemplate);
    }
}

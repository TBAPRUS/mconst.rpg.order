package mconst.rpg.order;

import mconst.rpg.order.models.dtos.CreatedOrderEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, CreatedOrderEvent> producerFactory() {
        var jsonSerializer = new JsonSerializer<CreatedOrderEvent>();
        jsonSerializer.setAddTypeInfo(false);

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, jsonSerializer);

        return new DefaultKafkaProducerFactory<>(
                configProps,
                new StringSerializer(),
                jsonSerializer
        );
    }

    @Bean
    public KafkaTemplate<String, CreatedOrderEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

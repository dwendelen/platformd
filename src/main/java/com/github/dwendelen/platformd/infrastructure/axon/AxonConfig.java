package com.github.dwendelen.platformd.infrastructure.axon;

import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.serialization.Converter;
import org.axonframework.serialization.FixedValueRevisionResolver;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public Serializer serializer() {
        return new JacksonSerializer();
    }
    //@Bean
    public EventHandlingConfiguration eventHandlingConfiguration() {
        return new EventHandlingConfiguration()
                .usingTrackingProcessors();
    }
}

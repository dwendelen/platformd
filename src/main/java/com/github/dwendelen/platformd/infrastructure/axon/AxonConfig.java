package com.github.dwendelen.platformd.infrastructure.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.bind.support.SpringWebConstraintValidatorFactory;

import javax.annotation.PostConstruct;

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

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
        return simpleCommandBus;
    }
}

package com.github.dwendelen.platformd;

import org.axonframework.spring.config.AnnotationDriven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ReflectionUtils;

@SpringBootApplication
@EnableScheduling
public class Application
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

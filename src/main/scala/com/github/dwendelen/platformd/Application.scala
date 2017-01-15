package com.github.dwendelen.platformd

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.dwendelen.platformd.infrastructure.authentication.UserVerifyingFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Primary}
import org.springframework.scheduling.annotation.EnableScheduling

object Application extends App
{
    SpringApplication.run(classOf[ApplicationConfig], args:_*)
}

@SpringBootApplication
@EnableScheduling
class ApplicationConfig
{
    @Bean
    def identityFilter(filter: UserVerifyingFilter): FilterRegistrationBean = {
        val filterRegistrationBean = new FilterRegistrationBean()
        filterRegistrationBean.setFilter(filter)
        filterRegistrationBean.addUrlPatterns("/api/*")
        filterRegistrationBean
    }

    @Bean
    @Primary
    def objectMapper() : ObjectMapper = {
        new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(DefaultScalaModule)
            .registerModule(new JavaTimeModule())
    }

    @Bean
    def cassandraObjectMapper() : ObjectMapper = {
         new ObjectMapper()
             .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
             .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
             .registerModule(DefaultScalaModule)
             .registerModule(new JavaTimeModule())
    }
}
package com.example.demo;

import com.example.demo.model.CustomerNumber;
import com.example.demo.model.Other;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Stream.of(
                new CustomerNumberSerializer(),
                new CustomerNumberDeserializer(),
                new OtherSerializer(),
                new OtherDeserializer()
        ).collect(Collectors.toUnmodifiableList()));
    }

    static class CustomerNumberDeserializer implements Converter<String, CustomerNumber> {
        @Override
        public CustomerNumber convert(String source) {
            return new CustomerNumber(source);
        }
    }

    static class CustomerNumberSerializer implements Converter<CustomerNumber, String> {
        @Override
        public String convert(CustomerNumber source) {
            return source.getValue();
        }
    }

    static class OtherDeserializer implements Converter<String, Other> {
        @Override
        public Other convert(String source) {
            return new Other(source);
        }
    }

    static class OtherSerializer implements Converter<Other, String> {
        @Override
        public String convert(Other source) {
            return source.getValue();
        }
    }
}

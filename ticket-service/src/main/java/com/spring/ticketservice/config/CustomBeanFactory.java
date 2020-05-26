package com.spring.ticketservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.spring.ticketservice")
@EnableElasticsearchRepositories
public class CustomBeanFactory {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
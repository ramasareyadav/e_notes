package com.e_notes.config;

import org.hibernate.annotations.Bag;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}

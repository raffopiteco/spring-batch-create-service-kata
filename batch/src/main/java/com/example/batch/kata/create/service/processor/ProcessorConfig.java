package com.example.batch.kata.create.service.processor;

import com.example.kata.model.People;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {
  
  @Bean
  public ItemProcessor<People,People> uppercasePeopleProcessor() {
    return new UppercasePeopleProcessor();
  }
}

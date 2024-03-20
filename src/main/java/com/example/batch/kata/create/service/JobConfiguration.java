package com.example.batch.kata.create.service;

import com.example.batch.kata.create.service.model.People;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {
  @Bean
  public Step uppercasePeopleStep(ItemReader flatFileItemReader, ItemProcessor<People,People> uppercasePeopleProcessor,
                                  ItemWriter jdbcBatchItemWriter) {
    
  }
}

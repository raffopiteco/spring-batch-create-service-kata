package com.example.batch.kata.create.service.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReaderConfig {

  @Bean
  public ItemReader peopleReader() {
    return new PeopleReader("sample-data.csv");
  }
}

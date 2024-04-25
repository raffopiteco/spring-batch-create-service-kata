package com.example.batch.kata.create.service.context;

import static com.example.batch.kata.create.service.context.ContextConstant.PROCESSED_PEOPLE_KEY;

import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionContextConfiguration {

  @Bean
  public ExecutionContextPromotionListener promotionListener() {
    ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
    listener.setKeys(new String[] {PROCESSED_PEOPLE_KEY});
    return listener;
  }

}

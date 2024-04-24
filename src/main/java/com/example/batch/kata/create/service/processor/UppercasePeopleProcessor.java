package com.example.batch.kata.create.service.processor;

import com.example.batch.kata.create.service.model.People;
import org.springframework.batch.item.ItemProcessor;

public class UppercasePeopleProcessor implements ItemProcessor<People, People> {

  @Override
  public People process(People people) throws Exception {
    return new People(people.getFirstname().toUpperCase(), people.getLastname().toUpperCase());
  }
}

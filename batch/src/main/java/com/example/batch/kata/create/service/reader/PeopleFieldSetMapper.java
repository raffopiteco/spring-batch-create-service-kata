package com.example.batch.kata.create.service.reader;

import com.example.kata.model.People;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PeopleFieldSetMapper implements FieldSetMapper<People> {
  public People mapFieldSet(FieldSet fieldSet) {
    return new People(fieldSet.readString(0),
                               fieldSet.readString(1));
  }
}

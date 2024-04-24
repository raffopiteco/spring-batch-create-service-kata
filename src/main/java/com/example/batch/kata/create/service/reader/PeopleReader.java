package com.example.batch.kata.create.service.reader;

import com.example.batch.kata.create.service.model.People;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PeopleReader extends FlatFileItemReader<People> {

  public PeopleReader(String resource) {
    setResource(new ClassPathResource(resource));
    DefaultLineMapper<People> lineMapper = new DefaultLineMapper<>();
    //DelimitedLineTokenizer defaults to comma as its delimiter
    lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
    lineMapper.setFieldSetMapper(new PeopleFieldSetMapper());
    setLineMapper(lineMapper);
  }
}

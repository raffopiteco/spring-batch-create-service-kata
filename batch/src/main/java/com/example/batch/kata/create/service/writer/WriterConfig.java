package com.example.batch.kata.create.service.writer;

import com.example.kata.model.People;
import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterConfig {

  @Bean
  public JdbcBatchItemWriter jdbcPeopleWriter(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<People>()
            .sql("INSERT INTO people (firstname, lastname) VALUES (:firstname, :lastname)")
            .dataSource(dataSource)
            .beanMapped()
            .build();

  }
}

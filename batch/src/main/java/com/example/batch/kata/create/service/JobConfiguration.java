package com.example.batch.kata.create.service;

import com.example.batch.kata.create.service.listener.PeopleJobExecutionListener;
import com.example.kata.model.People;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "hsqlDataSource",
        executionContextSerializerRef = "jackson2ExecutionContextStringSerializer",
        maxVarCharLength = 1000)
public class JobConfiguration {

  @Bean
  public DataSource hsqlDataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    return embeddedDatabaseBuilder
            .addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
            .addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
            .setType(EmbeddedDatabaseType.HSQL).build();
  }

  @Bean
  public ExecutionContextSerializer jackson2ExecutionContextStringSerializer() {
    return new Jackson2ExecutionContextStringSerializer();
  }

  @Bean
  public Step uppercasePeopleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader peopleReader,
                                  ItemProcessor<People, People> uppercasePeopleProcessor, ItemWriter jdbcPeopleWriter,
                                  ExecutionContextPromotionListener promotionListener) {
    return new StepBuilder("uppercasePeopleStep", jobRepository)
            .<People, People>chunk(10, transactionManager)
            .listener(promotionListener)
            .reader(peopleReader)
            .processor(uppercasePeopleProcessor)
            .writer(jdbcPeopleWriter)
            .build();
  }

  @Bean
  public JobExecutionListener peopleJobExecutionListener() {
    return new PeopleJobExecutionListener();
  }

  @Bean
  public Job peopleJob(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager,
                      Step uppercasePeopleStep,
                      JobExecutionListener peopleJobExecutionListener) {
    return new JobBuilder("personJob", jobRepository)
            .start(uppercasePeopleStep)
            .listener(peopleJobExecutionListener)
            .build();
  }
}

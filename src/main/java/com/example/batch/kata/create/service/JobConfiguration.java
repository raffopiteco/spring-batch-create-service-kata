package com.example.batch.kata.create.service;

import com.example.batch.kata.create.service.model.People;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {

  @Bean
  public Step uppercasePeopleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader peopleReader,
                                  ItemProcessor<People, People> uppercasePeopleProcessor, ItemWriter jdbcPeopleWriter) {
    return new StepBuilder("uppercasePeopleStep", jobRepository)
            .<People, People>chunk(10, transactionManager)
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
  public Job peopleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      Step uppercasePeopleStep,
                      JobExecutionListener peopleJobExecutionListener) {
    return new JobBuilder("personJob", jobRepository)
            .start(uppercasePeopleStep)
            .listener(peopleJobExecutionListener)
            .build();
  }
}

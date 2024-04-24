package com.example.batch.kata.create.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class PeopleJobExecutionListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(PeopleJobExecutionListener.class);
  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Job started");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    log.info("Job ended");
  }
}

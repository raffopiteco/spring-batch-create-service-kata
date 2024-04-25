package com.example.batch.kata.create.service;

import static com.example.batch.kata.create.service.context.ContextConstant.PROCESSED_PEOPLE_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.kata.model.People;
import com.example.batch.kata.util.CleanUpAfterTestExtension;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(CleanUpAfterTestExtension.class)
@SpringBatchTest
@SpringBootTest
class BatchCreateServiceKataApplicationIT {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private JobRepositoryTestUtils jobRepositoryTestUtils;
  @Autowired
  private Job peopleJob;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jobLauncherTestUtils.setJob(peopleJob);
  }

  @AfterEach
  void tearDown() {
    jobRepositoryTestUtils.removeJobExecutions();
  }

  @Test
  void importUserJob_WhenJobEnds_ThenStatusCompleted() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
  }

  @Test
  void importUserJob_executeUppercaseConversion_thenNoLowercaseAreStoredInDatabase() throws Exception {
    jobLauncherTestUtils.launchJob();

    RowMapper<People> peopleRowMapper = (rs, rowNumber) -> new People(rs.getString("firstname"), rs.getString("lastname"));

    List<People> peoplesUppercase = jdbcTemplate.query(
            "SELECT firstname, lastname FROM people where firstname='PEARLINE' and lastname='MODIE'", peopleRowMapper);
    List<People> peoplesLowercase = jdbcTemplate.query(
            "SELECT firstname, lastname FROM people where firstname='Pearline' and lastname='Modie'", peopleRowMapper);

    assertThat(peoplesUppercase).hasSize(2);
    assertThat(peoplesLowercase).isEmpty();
  }

  @Test
  void importUserJob_executeUppercaseConversion_thenTheCorrectContextIsSavedInDB() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    JobInstance jobInstance = jobExecution.getJobInstance();
    JobParameters jobParameters = jobExecution.getJobParameters();
    JobExecution lastJobExecution = jobLauncherTestUtils.getJobRepository().getLastJobExecution(jobInstance.getJobName(), jobParameters);

    List<People> processedPeople = lastJobExecution.getExecutionContext().get(PROCESSED_PEOPLE_KEY, List.class);

    assertThat(processedPeople).hasSize(11);
  }

  @Test
  void contextLoads() {
  }

}

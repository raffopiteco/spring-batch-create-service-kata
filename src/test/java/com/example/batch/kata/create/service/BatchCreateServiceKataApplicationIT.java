package com.example.batch.kata.create.service;

import com.example.batch.kata.create.service.model.People;
import com.example.batch.kata.create.service.repository.PeopleRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
				DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBatchTest
@SpringBootTest
@AutoConfigureTestEntityManager
class BatchCreateServiceKataApplicationIT {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;
	@Autowired
	private Job importUserJob;
	@Autowired
	private PeopleRepository peopleRepository;

	@BeforeEach
	void setUp() {
		jobLauncherTestUtils.setJob(importUserJob);
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

		List<People> peoplesUppercase = peopleRepository.findByFirstnameAndLastname("CHLORIS", "ESTELLA");
		List<People> peoplesLowercase = peopleRepository.findByFirstnameAndLastname("Chloris","Estella");

		assertThat(peoplesUppercase).hasSize(2);
		assertThat(peoplesLowercase).isEmpty();
	}

	@Test
	void contextLoads() {
	}

}

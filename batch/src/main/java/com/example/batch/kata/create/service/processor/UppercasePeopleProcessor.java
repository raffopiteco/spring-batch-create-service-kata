package com.example.batch.kata.create.service.processor;

import static com.example.batch.kata.create.service.context.ContextConstant.PROCESSED_PEOPLE_KEY;

import com.example.kata.model.People;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

public class UppercasePeopleProcessor implements ItemProcessor<People, People> {

  private StepExecution stepExecution;

  @Override
  public People process(People people) throws Exception {
    addProcessedPeopleToContext(people);
    return new People(people.getFirstname().toUpperCase(), people.getLastname().toUpperCase());
  }

  private void addProcessedPeopleToContext(People people) {
    ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
    List<People> processedPeoples = stepExecutionContext.get(PROCESSED_PEOPLE_KEY, List.class, new ArrayList<>());
    if(!stepExecutionContext.containsKey(PROCESSED_PEOPLE_KEY)) {
      stepExecutionContext.put(PROCESSED_PEOPLE_KEY, processedPeoples);
    }
    processedPeoples.add(people);
  }

  @BeforeStep
  public void retrieveStepExecution(StepExecution stepExecution) {
    this.stepExecution = stepExecution;
  }
}

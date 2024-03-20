package com.example.batch.kata.create.service.repository;

import com.example.batch.kata.create.service.model.People;
import java.util.List;

public interface PeopleRepository {
  List<People> findByFirstnameAndLastname(String firstname, String lastname);
}

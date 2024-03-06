package com.example.batch.kata.create.service;

import java.util.List;

public interface PeopleRepository {
  List<People> findByFirstnameAndLastname(String firstname, String lastname);
}

package ru.leverx.pets.service;

import ru.leverx.pets.entity.Person;

import java.util.List;

public interface PersonService {
    Person getPersonById(long id);
    List<Person> getAllPerson();
}

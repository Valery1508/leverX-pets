package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.entity.Person;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.service.PersonService;

import java.util.List;
import java.util.Objects;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    public PersonServiceImpl() {
        personDao = new PersonDao();
    }

    @Override
    public Person getPersonById(long id) {
        if (!checkPersonExistence(id)) {
            throw new EntityNotFoundException("Person with id = " + id + " does NOT exist!");
        }
        return personDao.getPersonById(id);
    }

    @Override
    public List<Person> getAllPerson() {
        return personDao.getAllPerson();
    }

    private boolean checkPersonExistence(long id) {
        return Objects.nonNull(personDao.getPersonById(id));
    }

    //TODO при удалении чела, удалить всех его животных (cascade type)
}

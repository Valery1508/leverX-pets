package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.dto.PersonResponseDto;
import ru.leverx.pets.entity.Person;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.mapper.PersonMapper;
import ru.leverx.pets.service.PersonService;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;
    private PersonMapper personMapper;

    public PersonServiceImpl() {
        personDao = new PersonDao();
        personMapper = new PersonMapper();
    }

    @Override
    public PersonResponseDto getPersonById(long id) {
        if (!checkPersonExistence(id)) {
            throw new EntityNotFoundException("Person with id = " + id + " does NOT exist!");
        }
        return personMapper.toDto(personDao.getPersonById(id));
    }

    @Override
    public List<PersonResponseDto> getAllPerson() {
        return toDtos(personDao.getAllPerson());
    }

    private boolean checkPersonExistence(long id) {
        return Objects.nonNull(personDao.getPersonById(id));
    }

    @Override
    public List<PersonResponseDto> deletePersonById(int id) {
        if (checkPersonExistence(id)) {
            personDao.deletePersonById(id);
        } else {
            throw new EntityNotFoundException("Person with id = " + id + " does NOT exist!");
        }
        return getAllPerson();
    }

    @Override
    public PersonResponseDto addPerson(PersonRequestDto personRequestDto) {
        Person person = personMapper.toEntity(personRequestDto);
        personDao.savePerson(person);
        return getPersonById(person.getId());
    }

    private List<PersonResponseDto> toDtos(List<Person> persons) {
        return persons.stream().map(person -> personMapper.toDto(person)).collect(toList());
    }
}
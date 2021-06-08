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

//TODO
/*create, update - entity
* delete - void
* getById - optional*/

import static java.util.stream.Collectors.toList;

public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;

    public PersonServiceImpl(PersonDao personDao, PersonMapper personMapper) {
        this.personDao = personDao;
        this.personMapper = personMapper;
    }

    @Override
    public PersonResponseDto getPersonById(long id) {
        if (!checkPersonExistence(id)) {
            throw new EntityNotFoundException(id);
        }
        return personMapper.toDto(personDao.getPersonById(id));
    }

    @Override
    public List<PersonResponseDto> getAllPerson() {
        return toDtos(personDao.getAllPerson());
    }

    @Override
    public boolean checkPersonExistence(long id) {
        return Objects.nonNull(personDao.getPersonById(id));
    }

    @Override
    public void deletePersonById(long id) {
        if (checkPersonExistence(id)) {
            personDao.deletePersonById(id);
        } else {
            throw new EntityNotFoundException(id);
        }
    }

    @Override
    public PersonResponseDto addPerson(PersonRequestDto personRequestDto) {
        Person person = personMapper.toEntity(personRequestDto);
        personDao.savePerson(person);
        return getPersonById(person.getId());
    }

    @Override
    public PersonResponseDto updatePerson(long id, PersonRequestDto personRequestDto) {
        if (!checkPersonExistence(id)) {
            throw new EntityNotFoundException(id);
        }
        personRequestDto.setId(id);
        Person person = personMapper.toEntity(personRequestDto);
        personDao.updatePerson(person);
        return getPersonById(person.getId());
    }

    private List<PersonResponseDto> toDtos(List<Person> persons) {
        return persons.stream().map(personMapper::toDto).collect(toList());
    }
}

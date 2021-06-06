package ru.leverx.pets.mapper;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.dto.PersonResponseDto;
import ru.leverx.pets.entity.Person;

public class PersonMapper {
    private PetDao petDao;
    private PetMapper petMapper;

    public PersonMapper() {
        petDao = new PetDao();
        petMapper = new PetMapper();
    }

    public Person toEntity(PersonRequestDto personDto) {
        Person person = new Person();
        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        return person;
    }

    public PersonResponseDto toDto(Person person) {
        PersonResponseDto personDto = new PersonResponseDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setPets(petMapper.toDtos(person.getPets()));
        return personDto;
    }
}

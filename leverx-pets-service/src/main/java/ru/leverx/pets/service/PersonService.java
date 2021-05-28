package ru.leverx.pets.service;

import ru.leverx.pets.dto.PersonRequestDto;
import ru.leverx.pets.dto.PersonResponseDto;

import java.util.List;

public interface PersonService {
    PersonResponseDto getPersonById(long id);

    List<PersonResponseDto> getAllPerson();

    List<PersonResponseDto> deletePersonById(int id);

    PersonResponseDto addPerson(PersonRequestDto personRequestDto);
}

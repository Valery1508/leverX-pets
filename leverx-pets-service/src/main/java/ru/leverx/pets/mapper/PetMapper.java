package ru.leverx.pets.mapper;

import ru.leverx.pets.dao.PersonDao;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.entity.PetType;

public class PetMapper {
    private PersonDao personDao;

    public PetMapper() {
        personDao = new PersonDao();
    }

    public Pet toEntity(PetDto petDto) {
        Pet pet = new Pet();
        pet.setId(petDto.getId());
        pet.setName(petDto.getName());
        pet.setType(PetType.valueOf(petDto.getType()));
        pet.setPerson(personDao.getPersonById(petDto.getPersonId()));
        return pet;
    }

    public PetDto toDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setId(pet.getId());
        petDto.setName(pet.getName());
        petDto.setType(String.valueOf(pet.getType()));
        petDto.setPersonId(pet.getPerson().getId());
        return petDto;
    }
}

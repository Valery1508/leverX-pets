package ru.leverx.pets.service;

import ru.leverx.pets.dto.PetDto;

import java.util.List;

public interface PetService {
    List<PetDto> getAllPets();

    PetDto getPetById(long id);

    List<PetDto> deletePetById(long id);

    PetDto updatePet(long id, PetDto petDto);

    PetDto addPet(PetDto petDto);

}

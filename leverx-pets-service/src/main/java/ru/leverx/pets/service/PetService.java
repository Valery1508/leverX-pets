package ru.leverx.pets.service;

import ru.leverx.pets.dto.PetDto;

import java.util.List;

public interface PetService {
    List<PetDto> getAllPets();

    PetDto getPetById(int id);

    List<PetDto> deletePetById(int id);

}

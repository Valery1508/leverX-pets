package ru.leverx.pets.service;

import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.entity.Pet;

import java.util.List;

public interface PetService {
    List<Pet> getAllPets();
    PetDto getPetById(int id);
    List<Pet> deletePetById(int id);

}

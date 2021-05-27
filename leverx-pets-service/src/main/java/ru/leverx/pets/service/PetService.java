package ru.leverx.pets.service;

import ru.leverx.pets.entity.Pet;

import java.util.List;

public interface PetService {
    List<Pet> getAllPets();
    Pet getPetById(int id);
    List<Pet> deletePetById(int id);

}

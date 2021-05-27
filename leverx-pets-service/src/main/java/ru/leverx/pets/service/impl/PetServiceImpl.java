package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.service.PetService;

import java.util.List;
import java.util.Objects;

public class PetServiceImpl implements PetService {

    private PetDao petDao;

    public PetServiceImpl() {
        petDao = new PetDao();
    }

    @Override
    public List<Pet> getAllPets() {
        return petDao.getAllPets();
    }

    @Override
    public Pet getPetById(int id) {
        if(!checkPetExistence(id)){
            throw new EntityNotFoundException("Pet with id = " + id + " does NOT exist!");
        }
        return petDao.getPetById(id);
    }

    @Override
    public List<Pet> deletePetById(int id) {
        if (checkPetExistence(id)) {
            petDao.deletePetById(id);
        } else {
            throw new EntityNotFoundException("Pet with id = " + id + " does NOT exist!");
        }
        return getAllPets();
    }

    private boolean checkPetExistence(int id){
        return Objects.nonNull(petDao.getPetById(id));
    }
}

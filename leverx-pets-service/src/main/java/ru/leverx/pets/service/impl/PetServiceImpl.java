package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.mapper.PetMapper;
import ru.leverx.pets.service.PetService;

import java.util.List;
import java.util.Objects;

public class PetServiceImpl implements PetService {

    private PetDao petDao;
    private PetMapper petMapper;

    public PetServiceImpl() {
        petDao = new PetDao();
        petMapper = new PetMapper();
    }

    @Override
    public List<Pet> getAllPets() {
        return petDao.getAllPets();
    }

    @Override
    public PetDto getPetById(int id) {
        if(!checkPetExistence(id)){
            throw new EntityNotFoundException("Pet with id = " + id + " does NOT exist!");
        }
        Pet pet = petDao.getPetById(id);
        return petMapper.toDto(pet);
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

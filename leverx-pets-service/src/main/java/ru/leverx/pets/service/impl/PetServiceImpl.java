package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.entity.Person;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.mapper.PetMapper;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.service.PetService;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class PetServiceImpl implements PetService {

    private PetDao petDao;
    private PetMapper petMapper;
    private PersonService personService;

    public PetServiceImpl() {
        petDao = new PetDao();
        petMapper = new PetMapper();
        personService = new PersonServiceImpl();
    }

    @Override
    public List<PetDto> getAllPets() {
        return toDtos(petDao.getAllPets());
    }

    @Override
    public PetDto getPetById(long id) {
        if (!checkPetExistence(id)) {
            throw new EntityNotFoundException("Pet with id = " + id + " does NOT exist!");
        }
        Pet pet = petDao.getPetById(id);
        return petMapper.toDto(pet);
    }

    @Override
    public List<PetDto> deletePetById(long id) {
        if (checkPetExistence(id)) {
            petDao.deletePetById(id);
        } else {
            throw new EntityNotFoundException("Pet with id = " + id + " does NOT exist!");
        }
        return getAllPets();
    }

    @Override
    public PetDto addPet(PetDto petDto) {
        if(!personService.checkPersonExistence(petDto.getPersonId())){
            throw new EntityNotFoundException("Person with id = " + petDto.getPersonId() + " does NOT exist!");
        }
        Pet pet = petMapper.toEntity(petDto);
        petDao.savePet(pet);
        return getPetById(pet.getId());
    }

    private boolean checkPetExistence(long id) {
        return Objects.nonNull(petDao.getPetById(id));
    }

    private List<PetDto> toDtos(List<Pet> pets) {
        return pets.stream().map(pet -> petMapper.toDto(pet)).collect(toList());
    }
}

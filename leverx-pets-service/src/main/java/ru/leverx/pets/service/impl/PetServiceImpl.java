package ru.leverx.pets.service.impl;

import ru.leverx.pets.dao.PetDao;
import ru.leverx.pets.dto.PetDto;
import ru.leverx.pets.entity.Pet;
import ru.leverx.pets.exception.EntityNotFoundException;
import ru.leverx.pets.mapper.PetMapper;
import ru.leverx.pets.service.PersonService;
import ru.leverx.pets.service.PetService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;
    private final PersonService personService;
    private final PetDao petDao;

    public PetServiceImpl(PetDao petDao, PetMapper petMapper, PersonService personService) {
        this.petDao = petDao;
        this.petMapper = petMapper;
        this.personService = personService;
    }

    @Override
    public List<PetDto> getAllPets() {
        return toDtos(petDao.getAllPets());
    }

    @Override
    public PetDto getPetById(long id) {
        if (!checkPetExistence(id)) {
            throw new EntityNotFoundException(id);
        }
        return petMapper.toDto(petDao.getPetById(id));
    }

    @Override
    public void deletePetById(long id) {
        if (checkPetExistence(id)) {
            petDao.deletePetById(id);
        } else {
            throw new EntityNotFoundException(id);
        }
    }

    @Override
    public PetDto updatePet(long id, PetDto petDto) {
        if (!checkPetExistence(id)) {
            throw new EntityNotFoundException(id);
        } else if (!personService.checkPersonExistence(petDto.getPersonId())) {
            throw new EntityNotFoundException(petDto.getPersonId());
        }
        petDto.setId(id);
        Pet pet = petMapper.toEntity(petDto);
        petDao.updatePet(pet);
        //petDao.action(pet, (pet1, session)-> (Pet) session.update(pet));
        return getPetById(pet.getId());
    }

    @Override
    public PetDto addPet(PetDto petDto) {
        if (!personService.checkPersonExistence(petDto.getPersonId())) {
            throw new EntityNotFoundException(petDto.getPersonId());
        }
        Pet pet = petMapper.toEntity(petDto);
        petDao.savePet(pet);
        //petDao.action(pet, (pet1, session) -> (Pet) session.save(pet));
        return getPetById(pet.getId());
    }

    private boolean checkPetExistence(long id) {
        return Objects.nonNull(petDao.getPetById(id));
    }

    private List<PetDto> toDtos(List<Pet> pets) {
        return pets.stream().map(petMapper::toDto).collect(toList());
    }
}

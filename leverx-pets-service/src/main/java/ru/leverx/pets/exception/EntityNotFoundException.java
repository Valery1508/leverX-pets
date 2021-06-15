package ru.leverx.pets.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Object entity, long id) {
        super(entity + " with id = " + id + " does NOT exist!");
    }
}
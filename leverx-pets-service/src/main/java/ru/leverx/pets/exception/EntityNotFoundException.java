package ru.leverx.pets.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(long id) {
        super("Entity with id = " + id + " does NOT exist!");
    }
}

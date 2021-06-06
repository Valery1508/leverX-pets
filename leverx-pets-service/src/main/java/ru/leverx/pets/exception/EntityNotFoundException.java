package ru.leverx.pets.exception;

public class EntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1515502198795323189L;

    public EntityNotFoundException(long id) {
        super("Entity with id = " + id + " does NOT exist!");
    }
}

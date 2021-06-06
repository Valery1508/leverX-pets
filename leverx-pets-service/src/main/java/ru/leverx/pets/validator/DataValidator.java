package ru.leverx.pets.validator;

import javax.validation.*;
import java.util.Set;

public class DataValidator {

    public static <T> void validateData(T dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

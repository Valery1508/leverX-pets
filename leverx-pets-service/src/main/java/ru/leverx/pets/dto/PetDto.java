package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.leverx.pets.exception.ExceptionMessages.NULL_FIELD_MESSAGE;
import static ru.leverx.pets.exception.ExceptionMessages.NULL_OR_EMPTY_FIELD_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {

    protected Long id;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String name;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String type;

    @NotNull(message = NULL_FIELD_MESSAGE)
    private long personId;

    public PetDto(String name, String type, long personId) {
        this.name = name;
        this.type = type;
        this.personId = personId;
    }
}

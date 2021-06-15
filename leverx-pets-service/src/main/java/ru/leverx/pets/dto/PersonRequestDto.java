package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static ru.leverx.pets.exception.ExceptionMessages.NULL_OR_EMPTY_FIELD_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDto {

    protected Long id;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String firstName;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String lastName;

    public PersonRequestDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

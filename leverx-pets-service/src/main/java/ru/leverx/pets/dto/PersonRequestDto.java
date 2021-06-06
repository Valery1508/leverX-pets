package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static ru.leverx.pets.Constants.NULL_OR_EMPTY_FIELD_MESSAGE;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDto extends BaseDto {

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    @Valid
    private String firstName;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    @Valid
    private String lastName;
}

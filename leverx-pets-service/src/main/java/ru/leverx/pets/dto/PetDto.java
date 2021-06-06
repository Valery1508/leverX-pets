package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.leverx.pets.Constants.NULL_FIELD_MESSAGE;
import static ru.leverx.pets.Constants.NULL_OR_EMPTY_FIELD_MESSAGE;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto extends BaseDto {
    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String name;

    @NotBlank(message = NULL_OR_EMPTY_FIELD_MESSAGE)
    private String type;

    @NotNull(message = NULL_FIELD_MESSAGE)
    private long personId;
}

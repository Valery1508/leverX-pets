package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto extends BaseDto {
    @NotBlank(message = "Field cannot be null or empty!")
    private String name;

    @NotBlank(message = "Field cannot be null or empty!")
    private String type;

    @NotNull(message = "Field cannot be null!")
    private long personId;
}

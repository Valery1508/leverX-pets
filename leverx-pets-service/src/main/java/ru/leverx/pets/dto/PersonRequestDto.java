package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDto extends BaseDto {

    @NotBlank(message = "Field cannot be null or empty!")
    private String firstName;

    @NotBlank(message = "Field cannot be null or empty!")
    private String lastName;
}

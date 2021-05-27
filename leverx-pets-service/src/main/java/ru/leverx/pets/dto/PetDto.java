package ru.leverx.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto extends BaseDto {
    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotEmpty
    private long personId;
}

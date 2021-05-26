package ru.leverx.pets.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "person", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;    //TODO change to PetType

    @OneToMany(mappedBy = "person",
            fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Pet> pets = new ArrayList<>();
}
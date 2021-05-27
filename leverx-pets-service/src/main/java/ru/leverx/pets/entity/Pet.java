package ru.leverx.pets.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pet", schema = "public")
@NoArgsConstructor
public class Pet extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference  //без нее не хочет в json конвертить
    private Person person;

    public Pet(String name, PetType type) {
        this.name = name;
        this.type = type;
    }
}

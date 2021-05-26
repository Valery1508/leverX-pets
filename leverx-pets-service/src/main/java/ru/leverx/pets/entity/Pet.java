package ru.leverx.pets.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pet", schema = "public")
@NoArgsConstructor
public class Pet extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    //@Enumerated
    private String type;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

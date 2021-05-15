package com.pac.acentueaqui.models;

import com.pac.acentueaqui.models.users.School;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "class_seq", sequenceName = "class_seq",
        initialValue = 2, allocationSize = 1)
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    private String name;

    @ManyToOne
    private School school;

}

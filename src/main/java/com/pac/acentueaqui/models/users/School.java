package com.pac.acentueaqui.models.users;

import com.pac.acentueaqui.models.Class;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "school_seq", sequenceName = "school_seq",
        initialValue = 2, allocationSize = 1)
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_seq")
    @Column(name = "id")
    private Long code;

    @Column(unique=true, length = 20)
    @NotNull(message = "CNPJ não pode ser nulo")
    private String cnpj;

    @Column(unique=true)
    @Email
    private String email;

    @NotNull(message = "Telefone não pode ser nulo")
    @Column(length = 15)
    private String telephone;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "school")
    private List<Class> classes;

    @ManyToMany
    @JoinTable(
            name = "teacher_school",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;
}

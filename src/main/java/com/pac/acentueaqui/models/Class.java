package com.pac.acentueaqui.models;

import com.pac.acentueaqui.models.questions.Question;
import com.pac.acentueaqui.models.users.School;
import com.pac.acentueaqui.models.users.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "class_seq", sequenceName = "class_seq",
        initialValue = 2, allocationSize = 1)
public class Class extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    private String name;

    @ManyToOne
    private School school;

    @ManyToMany
    @JoinTable(
            name = "teacher_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "classes")
    private List<Question> questions;
}

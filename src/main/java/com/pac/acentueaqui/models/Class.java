package com.pac.acentueaqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pac.acentueaqui.models.questions.Question;
import com.pac.acentueaqui.models.users.School;
import com.pac.acentueaqui.models.users.Student;
import com.pac.acentueaqui.models.users.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "class_seq", sequenceName = "class_seq",
        initialValue = 2, allocationSize = 1)
public class Class{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private School school;

    @ManyToMany
    @JoinTable(
            name = "teacher_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "classe")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Student> students;

    @ManyToMany(mappedBy = "classes")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Question> questions;
}

package com.pac.acentueaqui.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.questions.QuestionStudent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "student_seq", sequenceName = "student_seq",
        initialValue = 2, allocationSize = 1)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @Column(name = "id")
    private Long code;

    @Column(unique=true, length = 20)
    @NotNull(message = "Matrícula não pode ser nulo")
    private String registration;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    private Class classe;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<QuestionStudent> questionStudents;
}

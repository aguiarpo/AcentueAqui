package com.pac.acentueaqui.models.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pac.acentueaqui.models.questions.QuestionStudent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @JsonFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "Data de nascimento não pode ser nulo")
    private LocalDate birthDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "student")
    private List<QuestionStudent> questionStudents;
}

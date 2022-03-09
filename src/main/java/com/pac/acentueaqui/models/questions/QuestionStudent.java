package com.pac.acentueaqui.models.questions;

import com.pac.acentueaqui.models.users.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "question_student_seq", sequenceName = "question_student_seq",
        initialValue = 2, allocationSize = 1)
public class QuestionStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_student_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Tempo não pode ser nulo")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm:ss")
    private Date publicationTime;

    @NotNull(message = "Tentativas não pode ser nulo")
    private Integer attempts;

    @NotNull(message = "Resposta certa não pode ser nulo")
    private Integer rightAnswer;

    @NotNull(message = "XP não pode ser nulo")
    private Integer xp;

    @ManyToOne
    @JoinColumn
    private Question question;


    @ManyToOne
    @JoinColumn
    private Student student;
}

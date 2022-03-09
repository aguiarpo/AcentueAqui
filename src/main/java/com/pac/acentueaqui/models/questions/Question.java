package com.pac.acentueaqui.models.questions;

import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.users.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "question_seq", sequenceName = "question_seq",
        initialValue = 2, allocationSize = 1)
public class Question{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @Column(name = "id")
    private Long code;

    @Column(length = 20)
    private String word;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Data inicial não pode ser nulo")
    private Date initialDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Data inicial não pode ser nulo")
    private Date finalDate;

    @NotNull(message = "Valor de xp não pode ser nulo")
    private Integer valueXP;

    @NotNull(message = "Quantidade de tentativas não pode ser nulo")
    private Integer amountOfAttempt;

    @NotNull(message = "Dica não pode ser nulo")
    private Boolean tip;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "accentuation_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "accentuation_id"))
    private List<Accentuation> accentuations;

    @ManyToMany
    @JoinTable(
            name = "class_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private List<Class> classes;

    @OneToMany(mappedBy = "question")
    private List<QuestionStudent> questionStudents;
}

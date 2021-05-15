package com.pac.acentueaqui.models.users;

import com.pac.acentueaqui.models.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "teacher_seq", sequenceName = "teacher_seq",
        initialValue = 2, allocationSize = 1)
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_seq")
    @Column(name = "id")
    private Long code;

    @Column(unique=true)
    @Email
    private String email;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "question")
    private List<Question> questions;

    @ManyToMany(mappedBy = "teachers")
    private List<School> schools;
}

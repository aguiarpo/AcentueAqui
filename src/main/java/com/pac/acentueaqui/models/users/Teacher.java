package com.pac.acentueaqui.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pac.acentueaqui.models.Class;
import com.pac.acentueaqui.models.questions.Question;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "teacher")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Question> questions;

    @ManyToMany(mappedBy = "teachers")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<School> schools;

    @ManyToMany(mappedBy = "teachers")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Class> classes;
}

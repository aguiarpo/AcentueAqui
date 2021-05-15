package com.pac.acentueaqui.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Email
    private String email;

    @OneToMany(mappedBy = "user")
    private List<User> users;
}

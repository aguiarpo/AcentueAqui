package com.pac.acentueaqui.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "admin_seq", sequenceName = "admin_seq",
        initialValue = 2, allocationSize = 1)
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
    @Column(name = "id")
    private Long code;

    @Column(unique=true)
    @Email
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}


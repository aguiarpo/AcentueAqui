package com.pac.acentueaqui.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pac.acentueaqui.models.Auditable;
import com.pac.acentueaqui.models.LevelsOfAccess;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq",
        initialValue = 2, allocationSize = 1)
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    @Column(length = 60)
    private String name;

    @NotNull(message = "Username não pode ser Nulo")
    private String username;

    @NotEmpty(message = "Senha não pode estar vazia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LevelsOfAccess levelsOfAccess;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private List<Admin> admins;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<School> schools;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Student> students;

    public void setBcryptPassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(this.password);
    }
}

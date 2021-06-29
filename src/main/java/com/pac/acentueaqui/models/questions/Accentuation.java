package com.pac.acentueaqui.models.questions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "accentuation_seq", sequenceName = "accentuation_seq",
        initialValue = 2, allocationSize = 1)
public class Accentuation{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accentuation_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    @Column(length = 60, unique = true)
    private String name;

    @NotNull(message = "Formato não pode ser Nulo")
    @Column(length = 1, unique = true)
    private String format;

    @ManyToMany(mappedBy = "accentuations")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Question> questions;
}

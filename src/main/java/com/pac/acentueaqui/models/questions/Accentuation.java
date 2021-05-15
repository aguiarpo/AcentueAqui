package com.pac.acentueaqui.models.questions;

import com.pac.acentueaqui.models.Auditable;
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
public class Accentuation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accentuation_seq")
    @Column(name = "id")
    private Long code;

    @NotNull(message = "Nome não pode ser Nulo")
    @Column(length = 60)
    private String name;

    @ManyToMany(mappedBy = "accentuations")
    private List<Question> questions;
}

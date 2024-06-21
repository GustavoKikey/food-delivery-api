package com.kakinohana.algaworksjpaestudo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.algaworksjpaestudo.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class) //Diz que é do mesmo grupo que cadastroRestaurante, para que possa deixar null em um POST de cozinha
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonProperty("Titulo") // Feito para trocar o nome
    @NotBlank
    @Column(nullable = false)
    private String nome;

    @JsonIgnore // Não mostra essa propriedade na requisição, pq senao na hora de serializar, ia ficar em loop
    @OneToMany(mappedBy = "cozinha") // Faz um mapeamento de um pra muitos (o contrario do que é restaurante pra cozinha)
    private List<Restaurante> restaurantes = new ArrayList<>();

}

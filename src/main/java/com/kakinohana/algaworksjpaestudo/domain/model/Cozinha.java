package com.kakinohana.algaworksjpaestudo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonProperty("Titulo") // Feito para trocar o nome
    @Column(nullable = false)
    private String nome;

    @JsonIgnore // Não mostra essa propriedade na requisição, pq senao na hora de serializar, ia ficar em loop
    @OneToMany(mappedBy = "cozinha") // Faz um mapeamento de um pra muitos (o contrario do que é restaurante pra cozinha)
    private List<Restaurante> restaurantes = new ArrayList<>();

}

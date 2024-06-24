package com.kakinohana.deliveryapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.deliveryapi.Groups;
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
public class Cuisine {

    @NotNull(groups = Groups.CuisineId.class) //Diz que é do mesmo grupo que cadastroRestaurante, para que possa deixar null em um POST de cozinha
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonProperty("Titulo") // Feito para trocar o nome
    @NotBlank
    @Column(nullable = false)
    private String name;

    @JsonIgnore // Não mostra essa propriedade na requisição, pq senao na hora de serializar, ia ficar em loop
    @OneToMany(mappedBy = "cuisine") // Faz um mapeamento de um pra muitos (o contrario do que é restaurante pra cozinha)
    private List<Restaurant> restaurants = new ArrayList<>();

}
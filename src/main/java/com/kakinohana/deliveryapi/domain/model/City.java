package com.kakinohana.deliveryapi.domain.model;

import com.kakinohana.deliveryapi.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Data // Vai gerar todos os getters, setters, hashcode, equals e etc (Lombok)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Vai pegar do ID pq tá com esse true
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Valid
    @ConvertGroup(to = Groups.StateId.class)
    @NotNull
    @ManyToOne // Diz que tem MUITAS cidades para UM state
    @JoinColumn(nullable = false)
    private State state;
}

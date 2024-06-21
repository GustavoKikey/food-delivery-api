package com.kakinohana.algaworksjpaestudo.domain.model;

import com.kakinohana.algaworksjpaestudo.Groups;
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
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Valid
    @ConvertGroup(to = Groups.EstadoId.class)
    @NotNull
    @ManyToOne // Diz que tem MUITAS cidades para UM estado
    @JoinColumn(nullable = false)
    private Estado estado;
}

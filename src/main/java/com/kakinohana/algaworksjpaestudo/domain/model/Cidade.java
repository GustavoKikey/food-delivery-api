package com.kakinohana.algaworksjpaestudo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data // Vai gerar todos os getters, setters, hashcode, equals e etc (Lombok)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Vai pegar do ID pq tá com esse true
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)

    private String nome;

    @ManyToOne // Diz que tem MUITAS cidades para UM estado
    @JoinColumn(nullable = false)
    private Estado estado;
}

package com.kakinohana.algaworksjpaestudo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.algaworksjpaestudo.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @PositiveOrZero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;
    
    //@JsonIgnore
    //@JsonIgnoreProperties("hibernateLazyInitializer") // Tira o erro de quando vai serializar e o Lazy não deixa
    @ConvertGroup(to = Groups.CozinhaId.class)
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false) //Faz isso caso precise colocar numa coluna com um nome diferente lá no banco
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded // Indica que to incorporando essa classe
    private Endereco endereco;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;


    @JsonIgnore
    @OneToMany (mappedBy = "restaurante")
    private List<Produto> produtos;

    //@JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

}

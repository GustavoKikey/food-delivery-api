package com.kakinohana.algaworksjpaestudo.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable // Diz que essa classe é incorporavel, que pode ser incorporada em uma entidade (uma parte de outra entidade)
public class Endereco {

    @Column(name = "endereco_cep")
    private String cep;

    @Column(name = "endereco_logradouro")
    private String logradouro;

    @Column(name = "endereco_numero")
    private String numero;

    @Column(name = "endereco_complemento")
    private String complemento;

    @Column(name = "endereco_bairro")
    private String bairro;

    @ManyToOne(fetch = FetchType.LAZY) // Precisa fazer isso pq cidade é outra tabela
    @JoinColumn(name = "endereco_cidade_id")
    private Cidade cidade;
}

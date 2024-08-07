package com.kakinohana.deliveryapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable // Diz que essa classe é incorporavel, que pode ser incorporada em uma entidade (uma parte de outra entidade)
public class Address {

    @Column(name = "address_cep")
    private String cep;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @ManyToOne(fetch = FetchType.LAZY) // Precisa fazer isso pq city é outra tabela
    @JoinColumn(name = "address_city_id")
    private City city;
}

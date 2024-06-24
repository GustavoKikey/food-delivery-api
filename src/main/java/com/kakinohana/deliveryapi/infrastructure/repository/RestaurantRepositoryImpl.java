package com.kakinohana.deliveryapi.infrastructure.repository;

import com.kakinohana.deliveryapi.domain.model.Restaurant;
import com.kakinohana.deliveryapi.domain.repository.RestaurantRepository;
import com.kakinohana.deliveryapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.kakinohana.deliveryapi.infrastructure.repository.spec.RestaurantSpecs.comFreteGratis;
import static com.kakinohana.deliveryapi.infrastructure.repository.spec.RestaurantSpecs.comNomeSemelhante;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    // Injeta o próprio repositorio e utiliza o Lazy para não dar erro, já que ficaria em loop
    @Autowired @Lazy
    private RestaurantRepository restauranteRepository;


    //Utilizando o Criteria API pra construir uma query personalizada com filtros dinamicos
    @Override
    public List<Restaurant> find(String nome,
                                 BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class); // Escolhe qual classe você vai fazer a query
        Root<Restaurant> root = criteria.from(Restaurant.class); // isso é igual a fazer "from Restaurant"

        var predicates = new ArrayList<Predicate>(); // ArrayList pra poder colocar vários predicados e ser dinamico

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("name"), "%" + nome + "%")); // Se tiver nome, adiciona na lista
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryTax"), taxaFreteInicial)); // se tiver taxa frete inicial, adiciona na lista
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryTax"), taxaFreteFinal)); // se tiver taxa final adiciona na lista
        }

        criteria.where(predicates.toArray(new Predicate[0])); // transforma a lista em um array e coloca elas

        TypedQuery<Restaurant> query = manager.createQuery(criteria); // faz a query usando os predicados
        return query.getResultList(); // retorna o resultlist padrão
    }

    // Specification
    @Override
    public List<Restaurant> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }


}

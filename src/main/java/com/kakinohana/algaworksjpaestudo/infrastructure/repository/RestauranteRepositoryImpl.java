package com.kakinohana.algaworksjpaestudo.infrastructure.repository;

import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.RestauranteRepository;
import com.kakinohana.algaworksjpaestudo.domain.repository.RestauranteRepositoryQueries;
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

import static com.kakinohana.algaworksjpaestudo.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.kakinohana.algaworksjpaestudo.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    // Injeta o próprio repositorio e utiliza o Lazy para não dar erro, já que ficaria em loop
    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;


    //Utilizando o Criteria API pra construir uma query personalizada com filtros dinamicos
    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class); // Escolhe qual classe você vai fazer a query
        Root<Restaurante> root = criteria.from(Restaurante.class); // isso é igual a fazer "from Restaurante"

        var predicates = new ArrayList<Predicate>(); // ArrayList pra poder colocar vários predicados e ser dinamico

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%")); // Se tiver nome, adiciona na lista
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial)); // se tiver taxa frete inicial, adiciona na lista
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal)); // se tiver taxa final adiciona na lista
        }

        criteria.where(predicates.toArray(new Predicate[0])); // transforma a lista em um array e coloca elas

        TypedQuery<Restaurante> query = manager.createQuery(criteria); // faz a query usando os predicados
        return query.getResultList(); // retorna o resultlist padrão
    }

    // Specification
    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }


}

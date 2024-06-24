package com.kakinohana.deliveryapi.domain.repository;

import com.kakinohana.deliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join r.cuisine")
    List<Restaurant> findAll();

    List<Restaurant> findByDeliveryTaxBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    //@Query("from Restaurant where nome like %:name% and cuisine.id = :id")

    //TODO verificar o motivo do erro
    //List<Restaurant> consultarPorNome(String nome, @Param("id") Long cozinha);

    //List<Restaurant> findByNameContainingAndCuisineId(String nome, Long cozinhaId);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String nome);

    List<Restaurant> findTop2ByNameContaining(String nome);

    int countByCuisineId(Long cozinha);
}

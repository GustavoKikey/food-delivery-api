package com.kakinohana.deliveryapi.domain.repository;

import com.kakinohana.deliveryapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurant> findComFreteGratis (String nome);
}

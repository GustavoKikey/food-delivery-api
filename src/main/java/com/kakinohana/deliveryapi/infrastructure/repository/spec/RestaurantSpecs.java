package com.kakinohana.deliveryapi.infrastructure.repository.spec;

import com.kakinohana.deliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> comFreteGratis(){
        return (root, query, builder) ->
                builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> comNomeSemelhante(String nome){
        return (root, query, builder) ->
                builder.like(root.get("nome"), "%" + nome + "%");
    }
}

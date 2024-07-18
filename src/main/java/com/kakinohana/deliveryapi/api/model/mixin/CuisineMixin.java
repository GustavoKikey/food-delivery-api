package com.kakinohana.deliveryapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.deliveryapi.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class CuisineMixin {

    @JsonIgnore // Não mostra essa propriedade na requisição, pq senao na hora de serializar, ia ficar em loop
    private List<Restaurant> restaurants = new ArrayList<>();

}

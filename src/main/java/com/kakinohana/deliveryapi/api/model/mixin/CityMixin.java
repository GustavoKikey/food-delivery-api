package com.kakinohana.deliveryapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kakinohana.deliveryapi.domain.model.State;

public class CityMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}

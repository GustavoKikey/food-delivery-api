package com.kakinohana.deliveryapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kakinohana.deliveryapi.domain.model.Address;
import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.model.PaymentMethod;
import com.kakinohana.deliveryapi.domain.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMixin {

    //@JsonIgnoreProperties("hibernateLazyInitializer") // Tira o erro de quando vai serializar e o Lazy não deixa
    @JsonIgnoreProperties(value = "name", allowGetters = true) // Ignora o nome na desserialização porém mostra quando tiver métodos de GET
    private Cuisine cuisine;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private LocalDateTime registerDate;

    @JsonIgnore
    private LocalDateTime updateDate;

    @JsonIgnore
    private List<Product> products;

    @JsonIgnore
    private List<PaymentMethod> paymentMethods = new ArrayList<>();
}

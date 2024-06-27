package com.kakinohana.deliveryapi.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakinohana.deliveryapi.core.validation.ValidationException;
import com.kakinohana.deliveryapi.domain.exception.CuisineNotFoundException;
import com.kakinohana.deliveryapi.domain.exception.BusinessException;
import com.kakinohana.deliveryapi.domain.model.Restaurant;
import com.kakinohana.deliveryapi.domain.repository.RestaurantRepository;
import com.kakinohana.deliveryapi.domain.service.RegisterRestaurantService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<Restaurant> list(){
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant find(@PathVariable Long restaurantId){
        return registerRestaurantService.findOrFail(restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody @Valid Restaurant restaurant) {
        try {
            return registerRestaurantService.save(restaurant);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public Restaurant update(@PathVariable("restaurantId") Long restaurantId, @RequestBody @Valid Restaurant restaurant){
        try {
            Restaurant restaurantAtual = registerRestaurantService.findOrFail(restaurantId);

            BeanUtils.copyProperties(restaurant, restaurantAtual,
                    "id", "paymentMethod", "address", "registerDate", "products");


            return registerRestaurantService.save(restaurantAtual);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdate(@PathVariable("restaurantId") Long restaurantId, @RequestBody Map<String, Object> fields, HttpServletRequest request){

        Restaurant restaurantAtual = registerRestaurantService.findOrFail(restaurantId);

        merge (fields, restaurantAtual, request);
        validate(restaurantAtual, "restaurante");

        return update(restaurantId, restaurantAtual);
    }

    private void validate(Restaurant restaurant, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
        validator.validate(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private void merge(Map<String, Object> originData, Restaurant targetRestaurant, HttpServletRequest request){
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant originRestaurant = objectMapper.convertValue(originData, Restaurant.class);

            originData.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, originRestaurant);

                ReflectionUtils.setField(field, targetRestaurant, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") Long restaurantId){
            registerRestaurantService.excluir(restaurantId);
    }
}

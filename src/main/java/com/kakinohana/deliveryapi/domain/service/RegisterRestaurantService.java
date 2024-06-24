package com.kakinohana.deliveryapi.domain.service;

import com.kakinohana.deliveryapi.domain.exception.EntityInUseException;
import com.kakinohana.deliveryapi.domain.exception.RestaurantNotFoundException;
import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.model.Restaurant;
import com.kakinohana.deliveryapi.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RegisterRestaurantService {

    public static final String MSG_RESTAURANTE_EM_USO
            = "Restaurant de código %d não pode ser removida";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterCuisineService registerCuisineService;

    public Restaurant save(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = registerCuisineService.findOrFail(cuisineId);

        restaurant.setCuisine(cuisine);

        return restaurantRepository.save(restaurant);
    }

    public void excluir(Long restaurantId) {
        try {
            restaurantRepository.deleteById(restaurantId);
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(restaurantId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANTE_EM_USO, restaurantId));
        }
    }

    public Restaurant findOrFail(Long restaurantId){
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}

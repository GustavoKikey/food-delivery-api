package com.kakinohana.deliveryapi.domain.service;

import com.kakinohana.deliveryapi.domain.exception.CuisineNotFoundException;
import com.kakinohana.deliveryapi.domain.exception.EntityInUseException;
import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisterCuisineService {

    private static final String MSG_COZINHA_EM_USO
            = "Cuisine de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CuisineRepository cuisineRepository;

    @Transactional
    public Cuisine save(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    @Transactional
    public void delete(Long cuisineId) {
        try {
            cuisineRepository.deleteById(cuisineId);

        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(cuisineId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_COZINHA_EM_USO, cuisineId));
        }
    }

    public Cuisine findOrFail(Long cuisineId) {
        return cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }

}

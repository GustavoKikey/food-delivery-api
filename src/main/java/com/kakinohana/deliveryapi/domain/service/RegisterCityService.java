package com.kakinohana.deliveryapi.domain.service;

import com.kakinohana.deliveryapi.domain.exception.CityNotFoundException;
import com.kakinohana.deliveryapi.domain.exception.EntityInUseException;
import com.kakinohana.deliveryapi.domain.model.City;
import com.kakinohana.deliveryapi.domain.model.State;
import com.kakinohana.deliveryapi.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisterCityService {

    public static final String MSG_CIDADE_EM_USO = "Restaurant de código %d não pode ser removida";
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegisterStateService registerStateService;


    @Transactional
    public City save(City city){
        Long stateId = city.getState().getId();
        State state = registerStateService.findOrFail(stateId);

        city.setState(state);

        return cityRepository.save(city);
    }

    @Transactional
    public void delete(Long cityId){
        try {
            cityRepository.deleteById(cityId);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_CIDADE_EM_USO, cityId));
        }
    }

    public City findOrFail(Long cityId){
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }
}

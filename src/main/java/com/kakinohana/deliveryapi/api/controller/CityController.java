package com.kakinohana.deliveryapi.api.controller;

import com.kakinohana.deliveryapi.domain.exception.StateNotFoundException;
import com.kakinohana.deliveryapi.domain.exception.BusinessException;
import com.kakinohana.deliveryapi.domain.model.City;
import com.kakinohana.deliveryapi.domain.repository.CityRepository;
import com.kakinohana.deliveryapi.domain.service.RegisterCityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegisterCityService registerCityService;

    @GetMapping
    public List<City> find(){
        return cityRepository.findAll();
    }

    @GetMapping("/{cityId}")
    public City find(@PathVariable("cityId") Long cityId) {
        return registerCityService.findOrFail(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City add(@RequestBody @Valid City city){
        try {
            return registerCityService.save(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @PutMapping("/{cityId}")
    public City update(@PathVariable Long cityId,
                       @RequestBody @Valid City city) {
            try {
                City cityAtual = registerCityService.findOrFail(cityId);

                BeanUtils.copyProperties(city, cityAtual, "id");
                return registerCityService.save(cityAtual);

            } catch (StateNotFoundException e){
                throw new BusinessException(e.getMessage(), e);
            }
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cityId") Long cityId){
            registerCityService.delete(cityId);
    }

}

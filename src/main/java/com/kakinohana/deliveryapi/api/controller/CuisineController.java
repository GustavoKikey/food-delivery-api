package com.kakinohana.deliveryapi.api.controller;

import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.repository.CuisineRepository;
import com.kakinohana.deliveryapi.domain.service.RegisterCuisineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private RegisterCuisineService registerCuisineService;

    @GetMapping
    public List<Cuisine> list(){
        return cuisineRepository.findAll();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine find(@PathVariable Long cuisineId) {
        return registerCuisineService.findOrFail(cuisineId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine add(@RequestBody @Valid Cuisine cuisine){
        return registerCuisineService.save(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public Cuisine update(@PathVariable Long cuisineId,
                          @RequestBody @Valid Cuisine cuisine) {
        Cuisine cuisineAtual = registerCuisineService.findOrFail(cuisineId);

        BeanUtils.copyProperties(cuisine, cuisineAtual, "id");

        return registerCuisineService.save(cuisineAtual);
    }

    @DeleteMapping ("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cuisineId){
            registerCuisineService.delete(cuisineId);
    }


}

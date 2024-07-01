package com.kakinohana.deliveryapi;

import com.kakinohana.deliveryapi.domain.exception.EntityInUseException;
import com.kakinohana.deliveryapi.domain.exception.EntityNotFoundException;
import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.service.RegisterCuisineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RegisterCuisineOldIT {

    @Autowired
    private RegisterCuisineService registerCuisineService;

    @Test
    public void Success_WhenRegisterCuisine() {
        //cenário
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName("Teste");

        //ação
        newCuisine = registerCuisineService.save(newCuisine);

        //validação
        assertThat(newCuisine).isNotNull();
        assertThat(newCuisine.getId()).isNotNull();
    }

    @Test
    public void mustFail_WhenSaveCuisineWithoutName() {
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName(null);

        ConstraintViolationException expectedError =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    registerCuisineService.save(newCuisine);
                });

        assertThat(expectedError).isNotNull();
    }

    @Test
    public void mustFail_WhenDeleteCuisineInUse(){
        EntityInUseException expectedError =
                Assertions.assertThrows(EntityInUseException.class, () -> {
                    registerCuisineService.delete(1L);
                });
        assertThat(expectedError).isNotNull();
    }

    @Test
    public void mustFail_WhenDeleteCuisineNonexistent(){
        EntityNotFoundException expectedError =
                Assertions.assertThrows(EntityNotFoundException.class, () -> {
                    registerCuisineService.delete(100L);
                });
        assertThat(expectedError).isNotNull();
    }

}

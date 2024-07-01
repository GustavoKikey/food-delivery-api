package com.kakinohana.deliveryapi;

import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.repository.CuisineRepository;
import com.kakinohana.deliveryapi.util.DatabaseCleaner;
import com.kakinohana.deliveryapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") // Pega o banco de dados de teste
public class RegisterCuisineIT {


    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    private Integer cuisinesQuantity;
    private String correctJsonChineseCuisine;
    private Cuisine cuisineAmerican;
    public static final int CUISINE_ID_NONEXISTENT = 1000;

    @BeforeEach
    public void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cuisines";

        databaseCleaner.clearTables();
        prepareData();

        correctJsonChineseCuisine = ResourceUtils.getContentFromResource("/json/correct/cuisine.json");
    }

    @Test
    public void mustReturnStatus200_WhenFindAllCuisines(){

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustContainsAllCuisines_WhenFindAllCuisines(){

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", Matchers.hasSize(cuisinesQuantity));
    }

    @Test
    public void mustReturnStatus201_WhenRegisterCuisine(){
        RestAssured.given()
                    .body(correctJsonChineseCuisine)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void mustReturnsBodyAndStatusCorrects_WhenFindExistingCuisine(){
        RestAssured.given()
                    .pathParams("cuisineId", cuisineAmerican.getId())
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cuisineId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo(cuisineAmerican.getName()));
    }

    @Test
    public void mustReturnStatus404_WhenFindNonexistentCuisine(){
        RestAssured.given()
                .pathParams("cuisineId", CUISINE_ID_NONEXISTENT)
                .accept(ContentType.JSON)
                .when()
                .get("/{cuisineId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData(){
        Cuisine cuisineTai = new Cuisine();
        cuisineTai.setName("Tailandesa");
        cuisineRepository.save(cuisineTai);

        cuisineAmerican = new Cuisine();
        cuisineAmerican.setName("Americana");
        cuisineRepository.save(cuisineAmerican);

        cuisinesQuantity = (int) cuisineRepository.count();
    }

}

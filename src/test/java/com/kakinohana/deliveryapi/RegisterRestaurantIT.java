package com.kakinohana.deliveryapi;

import com.kakinohana.deliveryapi.domain.model.Cuisine;
import com.kakinohana.deliveryapi.domain.model.Restaurant;
import com.kakinohana.deliveryapi.domain.repository.CuisineRepository;
import com.kakinohana.deliveryapi.domain.repository.RestaurantRepository;
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

import java.math.BigDecimal;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") // Pega o banco de dados de teste
public class RegisterRestaurantIT {

    @LocalServerPort
    private Integer port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    private Integer restaurantsQuantity;
    private String correctJsonRestaurant;
    private String invalidCuisineRestaurant;
    private String noCuisineRestaurant;
    private String noDeliveryTaxRestaurant;

    @BeforeEach
    public void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        invalidCuisineRestaurant = ResourceUtils.getContentFromResource("/json/incorrect/restaurant-invalid-cuisine.json");
        correctJsonRestaurant = ResourceUtils.getContentFromResource("/json/correct/restaurant.json");
        noCuisineRestaurant = ResourceUtils.getContentFromResource("/json/incorrect/restaurant-no-cuisine.json");
        noDeliveryTaxRestaurant = ResourceUtils.getContentFromResource("/json/incorrect/restaurant-no-deliverytax.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void mustReturnStatus200andRestaurants_WhenFindAllRestaurants(){

        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", Matchers.hasSize(restaurantsQuantity));
    }

    @Test
    public void mustReturnStatus201_WhenRegisterRestaurant(){

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(correctJsonRestaurant)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void mustReturnStatus404_WhenFindNonexistentRestaurant(){

        RestAssured.given()
                .pathParams("restaurantId", 100)
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void mustReturnStatus400_WhenFindRestaurantWithInvalidCuisine(){

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(invalidCuisineRestaurant)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void mustReturnStatus400AndError_WhenFindRestaurantWithNoCuisine(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(noCuisineRestaurant)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo("Dados inválidos"));
    }

    @Test
    public void mustReturnStatus400AndError_WhenFindRestaurantWithNoDeliveryTax(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(noDeliveryTaxRestaurant)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo("Dados inválidos"));
    }

    @Test
    public void mustReturnStatus200AndRestaurant_WhenFindOneRestaurant(){
        RestAssured.given()
                .pathParams("restaurantId", 1)
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("Tai Restaurant"));
    }


    private void prepareData(){
        Cuisine cuisineTai = new Cuisine();
        cuisineTai.setName("Tailandesa");
        cuisineRepository.save(cuisineTai);

        Restaurant restaurantTai = new Restaurant();
        restaurantTai.setName("Tai Restaurant");
        restaurantTai.setDeliveryTax(new BigDecimal(10));
        restaurantTai.setCuisine(cuisineTai);
        restaurantRepository.save(restaurantTai);

        restaurantsQuantity = (int) restaurantRepository.count();
    }




}

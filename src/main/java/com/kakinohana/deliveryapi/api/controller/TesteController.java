//package com.kakinohana.deliveryapi.api.controller;
//
//import com.kakinohana.deliveryapi.domain.model.Cuisine;
//import com.kakinohana.deliveryapi.domain.model.Restaurant;
//import com.kakinohana.deliveryapi.domain.repository.CuisineRepository;
//import com.kakinohana.deliveryapi.domain.repository.RestaurantRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/teste")
//public class TesteController {
//
//    @Autowired
//    private CuisineRepository cuisineRepository;
//
//    @Autowired
//    private RestaurantRepository restauranteRepository;
//
//    @GetMapping("/cozinha/por-nome")
//    public List<Cuisine> cozinhasPorNome(String nome){
//        return cuisineRepository.findTodasByNomeContaining(nome);
//    }
//
//    @GetMapping("/cozinha/unica-por-nome")
//    public Optional<Cuisine> cozinhaPorNome(String nome){
//        return cuisineRepository.findByNome(nome);
//    }
//
//    @GetMapping("/restaurantes/por-taxa-frete")
//    public List<Restaurant> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
//        return restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal);
//    }
//
//    @GetMapping("/restaurantes/por-nome")
//    public List<Restaurant> restaurantesPorCozinhaId(String nome, Long cozinhaId){
//        return restauranteRepository.consultarPorNome(nome, cozinhaId);
//    }
//
//    @GetMapping("/restaurantes/por-nome-e-frete")
//    public List<Restaurant> restaurantesPorNomeFrete(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal){
//        return restauranteRepository.find(nome, freteTaxaInicial, freteTaxaFinal);
//    }
//
//    @GetMapping("/restaurantes/primeiro-por-nome")
//    public Optional<Restaurant> restaurantePrimeiroPorNome(String nome) {
//        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
//    }
//
//    @GetMapping("/restaurantes/top2-por-nome")
//    public List<Restaurant> restaurantesTop2PorNome(String nome) {
//        return restauranteRepository.findTop2ByNomeContaining(nome);
//    }
//
//    @GetMapping("/restaurantes/count-por-cozinha")
//    public int restaurantesCountPorCozinha(Long cozinhaId) {
//        return restauranteRepository.countByCozinhaId(cozinhaId);
//    }
//
//    // Specification
//    @GetMapping("/restaurantes/com-frete-gratis")
//    public List<Restaurant> restaurantesComFreteGratis(String nome){
//        return restauranteRepository.findComFreteGratis(nome);
//    }
//
//    @GetMapping("/restaurantes/primeiro")
//    public Optional<Restaurant> restaurantePrimeiro(){
//        return restauranteRepository.buscarPrimeiro();
//    }
//
//    @GetMapping("/cozinha/primeiro")
//    public Optional<Cuisine> cozinhaPrimeiro(){
//        return cuisineRepository.buscarPrimeiro();
//    }
//}

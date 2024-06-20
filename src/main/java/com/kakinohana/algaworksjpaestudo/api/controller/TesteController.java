package com.kakinohana.algaworksjpaestudo.api.controller;

import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.CozinhaRepository;
import com.kakinohana.algaworksjpaestudo.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.kakinohana.algaworksjpaestudo.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.kakinohana.algaworksjpaestudo.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinha/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome){
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinha/unica-por-nome")
    public Optional<Cozinha> cozinhaPorNome(String nome){
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorCozinhaId(String nome, Long cozinhaId){
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteTaxaInicial, BigDecimal freteTaxaFinal){
        return restauranteRepository.find(nome, freteTaxaInicial, freteTaxaFinal);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restaurantesTop2PorNome(String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/count-por-cozinha")
    public int restaurantesCountPorCozinha(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    // Specification
    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome){
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro(){
        return restauranteRepository.buscarPrimeiro();
    }

    @GetMapping("/cozinha/primeiro")
    public Optional<Cozinha> cozinhaPrimeiro(){
        return cozinhaRepository.buscarPrimeiro();
    }
}

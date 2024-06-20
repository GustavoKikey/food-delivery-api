package com.kakinohana.algaworksjpaestudo.domain.service;

import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeEmUsoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.exception.RestauranteNaoEncontradoException;
import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.CozinhaRepository;
import com.kakinohana.algaworksjpaestudo.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO
            = "Restaurante de código %d não pode ser removida";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException (restauranteId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
        }
    }

    public Restaurante buscarOuFalhar(Long restauranteId){
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}

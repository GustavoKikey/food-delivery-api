package com.kakinohana.algaworksjpaestudo.domain.service;

import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeEmUsoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EstadoNaoEncontradoException;
import com.kakinohana.algaworksjpaestudo.domain.model.Estado;
import com.kakinohana.algaworksjpaestudo.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Restaurante de código %d não pode ser removida";
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    };

    public void excluir(Long estadoId){
        try {
            estadoRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId){
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }

}

package com.kakinohana.algaworksjpaestudo.domain.service;

import com.kakinohana.algaworksjpaestudo.domain.exception.CidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeEmUsoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.model.Cidade;
import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import com.kakinohana.algaworksjpaestudo.domain.model.Estado;
import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.CidadeRepository;
import com.kakinohana.algaworksjpaestudo.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_EM_USO = "Restaurante de código %d não pode ser removida";
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;


    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId){
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId){
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }
}

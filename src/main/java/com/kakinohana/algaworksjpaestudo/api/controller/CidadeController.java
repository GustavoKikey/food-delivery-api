package com.kakinohana.algaworksjpaestudo.api.controller;

import com.kakinohana.algaworksjpaestudo.api.exceptionhandler.ApiError;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeEmUsoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EstadoNaoEncontradoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.NegocioException;
import com.kakinohana.algaworksjpaestudo.domain.model.Cidade;
import com.kakinohana.algaworksjpaestudo.domain.model.Estado;
import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.CidadeRepository;
import com.kakinohana.algaworksjpaestudo.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public List<Cidade> buscar(){
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable("cidadeId") Long cidadeId) {
        return cadastroCidade.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade){
        try {
            return cadastroCidade.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId,
                                       @RequestBody Cidade cidade) {
            try {
                Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

                BeanUtils.copyProperties(cidade, cidadeAtual, "id");
                return cadastroCidade.salvar(cidadeAtual);

            } catch (EstadoNaoEncontradoException e){
                throw new NegocioException(e.getMessage(), e);
            }
    }

    @DeleteMapping("/{cidadeId}")
    public void excluir(@PathVariable("cidadeId") Long cidadeId){
            cadastroCidade.excluir(cidadeId);
    }

}

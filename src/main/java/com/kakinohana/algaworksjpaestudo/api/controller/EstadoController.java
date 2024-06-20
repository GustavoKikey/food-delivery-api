package com.kakinohana.algaworksjpaestudo.api.controller;

import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeEmUsoException;
import com.kakinohana.algaworksjpaestudo.domain.exception.EntidadeNaoEncontradaException;
import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import com.kakinohana.algaworksjpaestudo.domain.model.Estado;
import com.kakinohana.algaworksjpaestudo.domain.model.Restaurante;
import com.kakinohana.algaworksjpaestudo.domain.repository.EstadoRepository;
import com.kakinohana.algaworksjpaestudo.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable("estadoId") Long estadoId){
       return cadastroEstado.buscarOuFalhar(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado){
        cadastroEstado.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId,
                            @RequestBody Estado estado) {
            Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

            BeanUtils.copyProperties(estado, estadoAtual, "id");

            return cadastroEstado.salvar(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    public void excluir(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }



}

package com.kakinohana.algaworksjpaestudo.domain.repository;

import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import com.kakinohana.algaworksjpaestudo.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    FormaPagamento buscar(Long id);
    FormaPagamento salvar(FormaPagamento formaPagamento);
    void excluir(FormaPagamento formaPagamento);

}

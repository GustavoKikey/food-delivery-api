package com.kakinohana.algaworksjpaestudo.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    MENSAGEM_INVALIDA("/mensagem-invalida", "Mensagem inválida"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_SISTEMA("/erro-de-sistema", "Erro de sistema");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://kikey.com.br" + path;
        this.title = title;
    }

}
package com.kakinohana.deliveryapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String msg) {
        super(msg);
    }

    public StateNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de estado com código %d", id));
    }
}

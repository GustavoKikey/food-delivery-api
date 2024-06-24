package com.kakinohana.deliveryapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String msg) {
        super(msg);
    }

    public CityNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de cidade com código %d", id));
    }
}

package com.kakinohana.deliveryapi.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CuisineNotFoundException(String msg) {
        super(msg);
    }

    public CuisineNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de cozinha com código %d", id));
    }
}

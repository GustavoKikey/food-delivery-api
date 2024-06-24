package com.kakinohana.deliveryapi.domain.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }

    //Pra stacktrace ficar mais completa
    public BusinessException(String msg, Throwable reason) {
        super(msg,reason);
    }
}

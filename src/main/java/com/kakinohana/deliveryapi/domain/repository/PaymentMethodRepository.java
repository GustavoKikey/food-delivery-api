package com.kakinohana.deliveryapi.domain.repository;

import com.kakinohana.deliveryapi.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodRepository {

    List<PaymentMethod> listar();
    PaymentMethod buscar(Long id);
    PaymentMethod salvar(PaymentMethod paymentMethod);
    void excluir(PaymentMethod paymentMethod);

}

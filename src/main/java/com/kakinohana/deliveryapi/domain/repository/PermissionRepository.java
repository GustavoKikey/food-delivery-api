package com.kakinohana.deliveryapi.domain.repository;

import com.kakinohana.deliveryapi.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {

    List<Permission> listar();
    Permission buscar(Long id);
    Permission salvar(Permission permission);
    void excluir(Permission permission);

}

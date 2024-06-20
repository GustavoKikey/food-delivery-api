package com.kakinohana.algaworksjpaestudo.domain.repository;

import com.kakinohana.algaworksjpaestudo.domain.model.Estado;
import com.kakinohana.algaworksjpaestudo.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}

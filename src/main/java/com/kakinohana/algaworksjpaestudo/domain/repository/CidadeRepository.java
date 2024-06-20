package com.kakinohana.algaworksjpaestudo.domain.repository;

import com.kakinohana.algaworksjpaestudo.domain.model.Cidade;
import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}

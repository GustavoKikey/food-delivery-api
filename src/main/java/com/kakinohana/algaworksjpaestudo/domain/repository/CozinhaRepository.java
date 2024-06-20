package com.kakinohana.algaworksjpaestudo.domain.repository;

import com.kakinohana.algaworksjpaestudo.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    // Se um método em uma interface JpaRepository tiver findBy, ela consegue implementar esse método sozinha
    // Contianing é outra keyword que faz uma query que acha qualquer coisa que contenha o que vc colocou
    List<Cozinha> findTodasByNomeContaining(String nome);

    Optional<Cozinha> findByNome(String nome);

    boolean existsByNome(String nome);
}

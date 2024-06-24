package com.kakinohana.deliveryapi.domain.repository;

import com.kakinohana.deliveryapi.domain.model.Cuisine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {

    // Se um método em uma interface JpaRepository tiver findBy, ela consegue implementar esse método sozinha
    // Contianing é outra keyword que faz uma query que acha qualquer coisa que contenha o que vc colocou
    List<Cuisine> findTodasByNameContaining(String nome);

    Optional<Cuisine> findByName(String nome);

    boolean existsByName(String nome);
}

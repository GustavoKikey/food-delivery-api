package com.kakinohana.algaworksjpaestudo;

import com.kakinohana.algaworksjpaestudo.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgaworksJpaEstudoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgaworksJpaEstudoApplication.class, args);
    }

}

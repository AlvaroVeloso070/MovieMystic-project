package com.ufg.moviemystic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufg.moviemystic.api.entities.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
}

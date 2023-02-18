package com.ufg.moviemystic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufg.moviemystic.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

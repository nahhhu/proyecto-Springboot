package com.techlab.spring.repository;

import com.techlab.spring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String name);

    Optional<Usuario> findByEmail(String email);
}

package com.techlab.spring.service.interfaces;

import com.techlab.spring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);
}

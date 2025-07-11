package com.techlab.spring.service;

import com.techlab.spring.model.Rol;
import com.techlab.spring.model.Usuario;
import com.techlab.spring.repository.RolRepository;
import com.techlab.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()){
            return "El nombre de usuario es obligatorio";
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()){
            return "Es obligatorio una contraseña";
        }
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()){
            return "El usuario ya existe";
        }
        Rol clienteRol = rolRepository.findByNombre("CLIENTE");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(Set.of(clienteRol));
        usuarioRepository.save(usuario);
        return "Usuario creado con exito";
    }


}


/*package com.techlab.spring.config;

import com.techlab.spring.model.Rol;
import com.techlab.spring.model.Usuario;
import com.techlab.spring.repository.RolRepository;
import com.techlab.spring.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        if (usuarioRepository.findByUsername("admin").isEmpty()){
            Rol rolAdmin = rolRepository.findByName("ROLE_ADMIN").orElseGet(() -> rolRepository.save(new Rol(null, "ROLE_ADMIN")));

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(List.of(rolAdmin));

            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado");
        }

        if (usuarioRepository.findByUsername("cliente").isEmpty()){
            Rol  rolCliente = rolRepository.findByName("ROLE_CLIENTE").orElseGet(()-> rolRepository.save(new Rol(null,"ROLE_CLIENTE")));

            Usuario  cliente = new Usuario();
            cliente.setUsername("cliente");
            cliente.setPassword(passwordEncoder.encode("cliente123"));
            cliente.setRoles(List.of(rolCliente));

            usuarioRepository.save(cliente);
            System.out.println("El usuario del cliente se ha creado");
        }
    }
    }

*/
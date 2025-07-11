package com.techlab.spring.service;

import com.techlab.spring.model.Usuario;
import com.techlab.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new  org.springframework.security.core.userdetails.User(usuario.getUsername(),usuario.getPassword(),usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase())).collect(Collectors.toList()));

    }
}

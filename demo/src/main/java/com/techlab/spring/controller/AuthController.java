package com.techlab.spring.controller;


import com.techlab.spring.model.Rol;
import com.techlab.spring.model.Usuario;
import com.techlab.spring.repository.RolRepository;
import com.techlab.spring.repository.UsuarioRepository;
import com.techlab.spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario){
        return authService.register(usuario);
    }
}

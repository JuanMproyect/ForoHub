package com.alura.ForoHub.service;


import com.alura.ForoHub.model.Usuario;
import com.alura.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String encriptarPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Usuario crerUsuario(String nombre, String email, String password){
        Usuario nuevoUsuario = new Usuario(nombre, email, password);
        return usuarioRepository.save(nuevoUsuario);
    }


}
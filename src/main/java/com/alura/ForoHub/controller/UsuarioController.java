package com.alura.ForoHub.controller;

import com.alura.ForoHub.dto.DatosDetalleUsuario;
import com.alura.ForoHub.dto.DatosRegistroUsuario;
import com.alura.ForoHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DatosDetalleUsuario> registrarUsuario(
            @RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
            UriComponentsBuilder uriComponentsBuilder) {

        // Encriptar la contraseña del usuario
        var passwEncript = usuarioService.encriptarPassword(datosRegistroUsuario.password());

        // Crear un nuevo usuario con los datos proporcionados
        var usuario = usuarioService.crerUsuario(
                datosRegistroUsuario.nombre(),
                datosRegistroUsuario.email(),
                passwEncript);

        // Crear los datos de detalle del usuario a partir del usuario creado
        DatosDetalleUsuario datosDetalleUsuario = new DatosDetalleUsuario(
                usuario.getNombre(),
                usuario.getEmail());

        // Construir la URL del nuevo recurso creado
        URI url = uriComponentsBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        // Devolver una respuesta con la URL del nuevo recurso y el cuerpo de datos de detalle del usuario
        return ResponseEntity.created(url).body(datosDetalleUsuario);
    }
}

package com.alura.ForoHub.dto;

import com.alura.ForoHub.model.Curso;
import com.alura.ForoHub.model.Topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        Curso nombreCurso,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,
        String estado
) {

    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getNombreCurso(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getAutor().getNombre(),
                topico.getEstado() ? "Abierto" : "Cerrado"
        );
    }

}
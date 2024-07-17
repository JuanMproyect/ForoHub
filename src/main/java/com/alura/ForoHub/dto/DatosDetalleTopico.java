package com.alura.ForoHub.dto;

import com.alura.ForoHub.model.Curso;
import com.alura.ForoHub.model.Topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        Curso nombreCurso,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado
) {

    public DatosDetalleTopico(Topico topico){
        this(
                topico.getId(),
                topico.getNombreCurso(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado() ? "Abierto" : "Cerrado"
        );
    }

}

package com.alura.ForoHub.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long idUsuario,
        String titulo,
        String mensaje
) {
}

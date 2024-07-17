package com.alura.ForoHub.controller;

import com.alura.ForoHub.dto.DatosActualizarTopico;
import com.alura.ForoHub.dto.DatosDetalleTopico;
import com.alura.ForoHub.dto.DatosListadoTopico;
import com.alura.ForoHub.dto.DatosRegistroTopico;
import com.alura.ForoHub.model.Topico;
import com.alura.ForoHub.repository.TopicoRepository;
import com.alura.ForoHub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//RestController para indicar a Spring que es un controller
//RequestMapping para mapear la ruta o path topicos
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TopicoService topicoService;


    @PostMapping
    public ResponseEntity<DatosDetalleTopico> RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                              UriComponentsBuilder uriComponentsBuilder){
        var topico = topicoService.crearTopico(datosRegistroTopico);
        DatosDetalleTopico datosDetalleTopico = new DatosDetalleTopico(topico.getId(), topico.getNombreCurso(),
                topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado() ? "Abierto" : "Cerrado");
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosDetalleTopico);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> retornaDatosTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosDetalleTopico(topico.getId(), topico.getNombreCurso(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado() ? "Abierto" : "Cerrado");
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico, @PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosDetalleTopico(topico.getId(), topico.getNombreCurso(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado() ? "Abierto" : "Cerrado"));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoService.validarExistencia(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 3) Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

}
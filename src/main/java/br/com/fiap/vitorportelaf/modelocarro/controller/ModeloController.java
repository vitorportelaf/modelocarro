package br.com.fiap.vitorportelaf.modelocarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloCreateRequest;
import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloResponse;
import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloUpdateRequest;
import br.com.fiap.vitorportelaf.modelocarro.mapper.ModeloMapper;
import br.com.fiap.vitorportelaf.modelocarro.model.Modelo;
import br.com.fiap.vitorportelaf.modelocarro.service.ModeloService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/${api.version}/modelos")
public class ModeloController {

    @Autowired
    private ModeloService service;

    @Autowired
    private ModeloMapper mapper;

    @PostMapping
    public ResponseEntity<ModeloResponse> create(@Valid @RequestBody ModeloCreateRequest dtoRequest) {
        Modelo modelo = service.createOrUpdate(mapper.toModel(dtoRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(modelo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponse> findById(@PathVariable Long id) {
        return service
                .findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponse>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponse> update(@PathVariable Long id,
            @RequestBody ModeloUpdateRequest dtoRequest) {

        if (service.findById(id).isPresent()) {
            Modelo modeloAlterado = service.createOrUpdate(mapper.toModel(id, dtoRequest));
            return ResponseEntity.ok(mapper.toDto(modeloAlterado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

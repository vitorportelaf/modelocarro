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
import br.com.fiap.vitorportelaf.modelocarro.repository.ModeloRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/${api.version}/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    @Autowired
    private ModeloMapper mapper;

    @PostMapping
    public ResponseEntity<ModeloResponse> create(@Valid @RequestBody ModeloCreateRequest dtoRequest) {
        Modelo modelo = repository.save(mapper.toModel(dtoRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(modelo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponse> findById(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponse>> findAll() {
        return ResponseEntity.ok(
                repository.findAll().stream()
                        .map(mapper::toDto)
                        .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponse> update(@PathVariable Long id,
                                                 @RequestBody ModeloUpdateRequest dtoRequest) {

        if (repository.existsById(id)) {
            Modelo modeloAlterado = repository.save(mapper.toModel(id, dtoRequest));
            return ResponseEntity.ok(mapper.toDto(modeloAlterado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

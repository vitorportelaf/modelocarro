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

import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaCreateRequest;
import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaResponse;
import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaUpdateRequest;
import br.com.fiap.vitorportelaf.modelocarro.mapper.MarcaMapper;
import br.com.fiap.vitorportelaf.modelocarro.model.Marca;
import br.com.fiap.vitorportelaf.modelocarro.repository.MarcaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/${api.version}/marcas")
public class MarcaController {

    @Autowired
    private MarcaRepository repository;

    @Autowired
    private MarcaMapper mapper;

    @PostMapping
    public ResponseEntity<MarcaResponse> create(@Valid @RequestBody MarcaCreateRequest dtoRequest) {
        Marca marca = repository.save(mapper.toModel(dtoRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(marca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> findById(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> findAll() {
        return ResponseEntity.ok(
                repository.findAll().stream()
                        .map(mapper::toDto)
                        .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> update(@PathVariable Long id,
                                                @RequestBody MarcaUpdateRequest dtoRequest) {

        if (repository.existsById(id)) {
            Marca marcaAlterada = repository.save(mapper.toModel(id, dtoRequest));
            return ResponseEntity.ok(mapper.toDto(marcaAlterada));
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

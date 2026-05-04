package br.com.fiap.vitorportelaf.modelocarro.controller;

import java.util.List;
import java.util.Optional;

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
import br.com.fiap.vitorportelaf.modelocarro.model.Modelo;
import br.com.fiap.vitorportelaf.modelocarro.repository.ModeloRepository;

@RestController
@RequestMapping("api/${api.version}/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    @PostMapping
    public ResponseEntity<Modelo> create(@RequestBody Modelo modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(modelo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> findById(@PathVariable Long id) {
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> update(@PathVariable Long id,
                                          @RequestBody Modelo modelo) {

        Optional<Modelo> optModelo = repository.findById(id);

        if (optModelo.isPresent()) {
            modelo.setId(id);
            Modelo modeloAlterado = repository.save(modelo);
            return ResponseEntity.ok(modeloAlterado);
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
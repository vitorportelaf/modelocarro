package br.com.fiap.vitorportelaf.modelocarro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.vitorportelaf.modelocarro.model.Modelo;
import br.com.fiap.vitorportelaf.modelocarro.repository.ModeloRepository;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    public Modelo createOrUpdate(Modelo modelo) {
        return repository.save(modelo);
    }

    public Optional<Modelo> findById(Long id) {
        return repository.findById(id);
    }

    public List<Modelo> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

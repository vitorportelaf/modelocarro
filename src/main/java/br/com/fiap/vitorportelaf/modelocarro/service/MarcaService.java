package br.com.fiap.vitorportelaf.modelocarro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.vitorportelaf.modelocarro.model.Marca;
import br.com.fiap.vitorportelaf.modelocarro.repository.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;

    public Marca createOrUpdate(Marca marca) {
        return repository.save(marca);
    }

    public Optional<Marca> findById(Long id) {
        return repository.findById(id);
    }

    public List<Marca> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

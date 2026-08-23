package br.com.fiap.vitorportelaf.modelocarro.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloCreateRequest;
import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloResponse;
import br.com.fiap.vitorportelaf.modelocarro.dto.ModeloUpdateRequest;
import br.com.fiap.vitorportelaf.modelocarro.model.Modelo;

@Component
public class ModeloMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Modelo toModel(ModeloCreateRequest dto) {
        return modelMapper.map(dto, Modelo.class);
    }

    public Modelo toModel(Long id, ModeloUpdateRequest dto) {
        Modelo modelo = modelMapper.map(dto, Modelo.class);
        modelo.setId(id);
        return modelo;
    }

    public ModeloResponse toDto(Modelo entity) {
        return modelMapper.map(entity, ModeloResponse.class);
    }
}

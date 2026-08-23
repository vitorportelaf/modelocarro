package br.com.fiap.vitorportelaf.modelocarro.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaCreateRequest;
import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaResponse;
import br.com.fiap.vitorportelaf.modelocarro.dto.MarcaUpdateRequest;
import br.com.fiap.vitorportelaf.modelocarro.model.Marca;

@Component
public class MarcaMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Marca toModel(MarcaCreateRequest dto) {
        return modelMapper.map(dto, Marca.class);
    }

    public Marca toModel(Long id, MarcaUpdateRequest dto) {
        Marca marca = modelMapper.map(dto, Marca.class);
        marca.setId(id);
        return marca;
    }

    public MarcaResponse toDto(Marca entity) {
        return modelMapper.map(entity, MarcaResponse.class);
    }
}

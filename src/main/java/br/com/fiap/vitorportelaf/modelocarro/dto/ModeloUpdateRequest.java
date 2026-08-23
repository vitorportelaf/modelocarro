package br.com.fiap.vitorportelaf.modelocarro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloUpdateRequest {

    private String nome;
    private Integer anoLancamento;
    private String tipoCombustivel;
    private Double precoBase;
    private String observacoes;
}

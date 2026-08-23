package br.com.fiap.vitorportelaf.modelocarro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloResponse {

    private Long id;
    private String nome;
    private Integer anoLancamento;
    private String tipoCombustivel;
    private Double precoBase;
    private String observacoes;
}

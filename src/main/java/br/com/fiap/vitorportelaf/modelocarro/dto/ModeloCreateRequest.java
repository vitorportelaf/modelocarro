package br.com.fiap.vitorportelaf.modelocarro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloCreateRequest {

    @NotNull(message = "O id do modelo é obrigatório")
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório")
    @Size(max = 100, message = "O nome do modelo deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "O ano de lançamento é obrigatório")
    @Positive(message = "O ano de lançamento deve ser positivo")
    private Integer anoLancamento;

    @NotBlank(message = "O tipo de combustível é obrigatório")
    @Size(max = 30, message = "O tipo de combustível deve ter no máximo 30 caracteres")
    private String tipoCombustivel;

    @NotNull(message = "O preço base é obrigatório")
    @PositiveOrZero(message = "O preço base não pode ser negativo")
    private Double precoBase;

    @Size(max = 255, message = "As observações devem ter no máximo 255 caracteres")
    private String observacoes;
}

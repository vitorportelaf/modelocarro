package br.com.fiap.vitorportelaf.modelocarro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcaCreateRequest {

    @NotNull(message = "O id da marca é obrigatório")
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(max = 100, message = "O nome da marca deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O país de origem é obrigatório")
    @Size(max = 50, message = "O país de origem deve ter no máximo 50 caracteres")
    private String paisOrigem;

    @NotNull(message = "O ano de fundação é obrigatório")
    @Positive(message = "O ano de fundação deve ser positivo")
    private Integer anoFundacao;

    @Size(max = 150, message = "O site oficial deve ter no máximo 150 caracteres")
    private String siteOficial;

    @NotNull(message = "É obrigatório informar se a marca está ativa")
    private Boolean ativa;
}

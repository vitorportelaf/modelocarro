package br.com.fiap.vitorportelaf.modelocarro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarcaResponse {

    private Long id;
    private String nome;
    private String paisOrigem;
    private Integer anoFundacao;
    private String siteOficial;
    private Boolean ativa;
}

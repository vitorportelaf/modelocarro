package br.com.fiap.vitorportelaf.modelocarro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome_marca", length = 100, nullable = false)
    private String nome;

    @Column(name = "pais_origem", length = 50, nullable = false)
    private String paisOrigem;

    @Column(name = "ano_fundacao", nullable = false)
    private Integer anoFundacao;

    @Column(name = "site_oficial", length = 150, nullable = true)
    private String siteOficial;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;
}


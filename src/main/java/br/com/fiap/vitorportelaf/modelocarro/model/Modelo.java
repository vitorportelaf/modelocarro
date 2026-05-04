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
@Table(name = "modelos")
public class Modelo {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome_modelo", length = 100, nullable = false)
    private String nome;

    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;

    @Column(name = "tipo_combustivel", length = 30, nullable = false)
    private String tipoCombustivel;

    @Column(name = "preco_base", nullable = false)
    private Double precoBase;

    @Column(name = "observacoes", length = 255, nullable = true)
    private String observacoes;
}

package br.com.aula.api_produtos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa um produto persistido pela aplicação
 * <p> Esta entidade contém os dados internos utilizados para camada
 * de persistência</p>
 */

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Produto {
    @Schema(description = "Identificador único de produto")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do produto")
    @Column(nullable = false , length = 100)
    private String nome;

    @Schema(description = "Preço do produto")
    @Column(nullable = false)
    private BigDecimal preco;

    @Schema(description = "Status do produto")
    @Column(nullable = false)
    private Boolean ativo;
}

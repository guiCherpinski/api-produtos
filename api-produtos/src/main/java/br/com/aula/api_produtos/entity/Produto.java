package br.com.aula.api_produtos.entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 100)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean ativo;
}

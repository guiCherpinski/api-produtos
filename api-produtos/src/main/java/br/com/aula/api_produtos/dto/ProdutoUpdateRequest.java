package br.com.aula.api_produtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Dados utilizados para atualizar completamente o produto
 *
 * @param nome novo nome do produto
 * @param preco novo preço do produto
 * @param ativo nova situação do produto
 */
@Schema(description = "Dados para a atualização de um produto")
public record ProdutoUpdateRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min=3, max=100)
        @Schema(example = "Monitor 27 polegadas")
        String nome,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser positivo")
        @Schema(example = "1999.00")
        BigDecimal preco,

        @NotNull(message = "O campos ativo é obrigatório")
        @Schema(example = "true")
        boolean ativo
) {}

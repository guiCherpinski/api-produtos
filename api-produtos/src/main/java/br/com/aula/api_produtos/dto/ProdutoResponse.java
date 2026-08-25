package br.com.aula.api_produtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Representação pública de um produto retornado pela API
 *
 * @param id identificação do produto
 * @param nome nome do produto
 * @param preco preço do produto
 * @param ativo situação atual do produto
 */
@Schema(description = "Dados de um produto retornado pela API")

public record ProdutoResponse (
   @Schema(description = "Identificador unico do produto", example = "1")
   Long id,

   @Schema(description = "Nome do produto", example = "Notebook Dell")
   String nome,

   @Schema(description = "Preço de venda do produto", example = "4500.00")
   BigDecimal preco,

   @Schema(description = "Indica se o produto está ativo", example = "true")
   boolean ativo
) {}

package br.com.aula.api_produtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Essa record define como deve ser a resposta do token
 * @param token
 */
@Schema(description = "Essa record define como deve ser a resposta do token")

public record DadosTokenJWT(
        @Schema(description = "código do token")
        String token
) {
}

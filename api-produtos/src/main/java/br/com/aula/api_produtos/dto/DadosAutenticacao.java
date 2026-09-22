package br.com.aula.api_produtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Essa record define o que deve ser passado para criação de um token JWT
 * @param username
 * @param password
 */
@Schema(description = "Define o que deve ser passado para criação de um token JWT")

public record DadosAutenticacao(
        @Schema(description = "username do usuário", example = "jonathan melo de alcantra sebastian")
        String username,
        @Schema(description = "senha do usuário", example = "batatinhaFrita123")
        String password
) {
}

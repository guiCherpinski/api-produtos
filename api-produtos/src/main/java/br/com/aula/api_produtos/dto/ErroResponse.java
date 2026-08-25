package br.com.aula.api_produtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;

@Schema(description = "Estrutura padronizada para respostas de erro da API")
public record ErroResponse(
        @Schema(description = "Código de Status HTTP", example = "404")
        Integer Status,

        @Schema(description = "Mensagem detalhada do erro", example = "Produto não encontrado 10: 1")
        String erro,

        @Schema(description = "Descrição curta do tipo de erro", example = "Recursos não encontrados")
        String mensagem,

        @Schema(description = "URL da requisição que originou o erro", example = "/produto/1")
        String caminho,

        @Schema(description = "Data e hora do erro", example = "2026-05-22:43:23:15")
        LocalDateTime timeStamp
) {
    /**
     * Construtor utilitario para gerar a resposta atribuindo a hora atual automaticamente
     */


    public static ErroResponse criar (Integer status, String erro, String mensagem, String caminho) {
        return new ErroResponse(status,erro,mensagem,caminho, LocalDateTime.now());
    }
}

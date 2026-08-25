package br.com.aula.api_produtos.controller;

import br.com.aula.api_produtos.dto.ErroResponse;
import br.com.aula.api_produtos.dto.ProdutoCreateRequest;
import br.com.aula.api_produtos.dto.ProdutoResponse;
import br.com.aula.api_produtos.dto.ProdutoUpdateRequest;
import br.com.aula.api_produtos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 * Controller REST responsável pelos endpoints relacionados ao recurso produto
 */

@Tag(
        name = "Produtos",
        description = "Operações relacionadas ao gerenciamento de produtos"
)

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    /**
     * Lista todos os produto cadastrados
     *
     * @return Lista de produtos
     */

    @Operation(
            summary = "Lista de produtos",
            description = "Retorna todos os produtos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produtos retornados com sucesso"
    )
    @GetMapping()
    public ResponseEntity<List<ProdutoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * Busca um produto pelo seu identificador
     *
     * @param id Identificador do produto
     * @return DTO {@link ProdutoResponse} com os dados do produto encontrado
     *
     */

    @Operation(
            summary = "Busca produto por ID",
            description = "Retorna os detalhes de um produto específico com base no seu identificador único"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @Parameter(description = "identificar únido do produto", example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * Busca produtos ativos pelo nome.
     *
     * @param nome Termo ou trecho do nome do produto para filtragem.
     * @return Lista contendo os DTOs {@link ProdutoResponse} encontrados.
     */
    @Operation(
            summary = "Busca produtos por nome",
            description = "Retorna uma lista de produtos ativos cujo nome contenha o termo informado (busca *case-insensitive*)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro de busca inválido",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
            )
    })
    @GetMapping(params = "nome")
    public ResponseEntity<List<ProdutoResponse>> buscarPorNome(
            @Parameter(description = "Termo ou palavra-chave contida no nome do produto", example = "Mouse")
            @RequestParam String nome
    ) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    /**
     * Cadastra um novo produto
     *
     * @param request DTO com os dados necessários pra criação do produto
     * @return DTO {@link ProdutoResponse} com o produto cadastrado e cabeçalho Location
     *
     */
    @Operation(
            summary = "Cadastro de um produto",
            description = "Cria um novo produto no catálogo e retona o recurso criado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoCreateRequest request) {
        ProdutoResponse produto = service.cadastrar(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.id())
                .toUri();

        return ResponseEntity.created(uri).body(produto);
    }

    /**
     * Atualiza os dados de um produto existente
     * @param id Identificador do produto a ser atualizado
     * @param request DTO co os novos dados do produto
     * @return DTO {@link ProdutoResponse} atualizado
     * */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @Parameter(description = "identificador único do produto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProdutoUpdateRequest request
    ){
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    /**
     * Remove um produto do catálogo
     * @param id Identificador do produto a ser removido
     * @return Resposta sem conteúdo (HTTP 204 No Content)
     * */
    @Operation (
            summary = "Remove um produto",
            description = "Realiza a exclusão do produto com base no seu identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "Identificador único do produto", example = "1")
            @PathVariable Long id
    ){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}

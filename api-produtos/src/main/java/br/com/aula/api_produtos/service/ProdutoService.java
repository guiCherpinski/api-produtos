package br.com.aula.api_produtos.service;

import br.com.aula.api_produtos.dto.ProdutoCreateRequest;
import br.com.aula.api_produtos.dto.ProdutoResponse;
import br.com.aula.api_produtos.dto.ProdutoUpdateRequest;
import br.com.aula.api_produtos.entity.Produto;
import br.com.aula.api_produtos.exception.ProdutoNaoEncontradoException;
import br.com.aula.api_produtos.mapper.ProdutoMapper;
import br.com.aula.api_produtos.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Serviço responsável pelas regras do negócio * relacionadas ao gerenciamento de produtos
 */

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoService(ProdutoRepository repository , ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     *  Cadastra um novo produto na base de dados após validar a unicidade do nome
     * @param request Objeto contendo os dados de entrada para criação do produto
     * @return DTO {@link ProdutoResponse} com os dados do produto persistido
     * @throws IllegalArgumentException Se já existir um produto cadastrado com o mesmo nome
     */

    @Transactional
    public ProdutoResponse cadastrar (ProdutoCreateRequest request) {
        if (repository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe um produto com esse nome");
        }
        // Converte DTO para entity
        Produto produto = mapper.toEntity(request);
        produto.setAtivo(true);
        Produto salvo = repository.save(produto);

        // Converte entity -> DTO response
        return mapper.toResponse(salvo);
    }

    /**
     * Retorna todos os produtos cadastrados
     * @return Lista de DTOs {@link ProdutoResponse}
     * representado os produtos encontrados.Lista vazia caso nenhum produto seja encontrado
     */

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar() {

        List<Produto> produtos = repository.findAll();

        return mapper.toResponseList(produtos);
    }

    /**
     * Busca um produto pelo seu identificador único
     * @param id Identificador do produto a ser localizado
     * @return DTO {@link ProdutoResponse} representa o produto encontrado
     * @throws ProdutoNaoEncontradoException Se nenhum produto for encontrado com o ID informado
     */

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId (Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado para ID: "+id));
    }

    /**
     * Busca produtos cujo nome contenha o termo informado (case insensitive)
     * @param nome Termo ou trecho do nome do produto a ser pesquisado
     * @return Lista de DTOs {@link ProdutoResponse} correspondentes ao termo informado
     * */
    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarPorNome(String nome){
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Atualiza todos os dados de um produto existente
     *
     * @param id Identificador do produto a ser atualizado
     * @param request DTO co os novos dados do produto
     * @return DTO {@link ProdutoResponse} com os dados do produto atualizados
     * @throws ProdutoNaoEncontradoException Se nenhum produto for encontrado para o ID informado
     */
    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoUpdateRequest request){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o ID " + id));

        mapper.updateEntity(request, produto);

        Produto atualizado = repository.save(produto);

        return mapper.toResponse(atualizado);
    }

    /**
     * Remove um produto da bas de dados pelo seu identificador
     * @param id Identificador do produto a ser removido
     * @throws ProdutoNaoEncontradoException Se nennhum produto for encontrado com o ID informado
     * */

    @Transactional
    public void remover(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id));

        repository.delete(produto);
    }



}

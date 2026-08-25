package br.com.aula.api_produtos.repository;

import br.com.aula.api_produtos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository

/**
 * Repositório responsável pelo acesso dos dados de produtos
 */

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    /**
     * Verifica se existe produto com o nome informado
     * Ignora a diferença entre letras maiúsculas e minúsculas
     * @param nome nome a ser pesquisado
     * @return {@code true} caso o produto exista com o nome
     */

    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Busca produtos cujo nome contenha o texto informado
     * @param nome parte do nome do produto
     * @return produtos encontrados
     */

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}

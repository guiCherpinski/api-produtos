package br.com.aula.api_produtos.config;

import br.com.aula.api_produtos.entity.Produto;
import br.com.aula.api_produtos.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.*;

/** Carga inicial de dados para popular o banco durante a inicialização da aplicação */

@Configuration
public class CargaDadosInicial implements CommandLineRunner {
    private final ProdutoRepository repository;

    public CargaDadosInicial(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String ... args) throws Exception{
        // popula apenas se o banco de dados estiver vazio
        if (repository.count() == 0) {
            List<Produto> produtosIniciais = List.of(
                    Produto.builder()
                            .nome("Notebook Dell Inspiron")
                            .preco(new BigDecimal("4500.00"))
                            .ativo(true)
                            .build(),
                    Produto.builder()
                            .nome("Mouse Gamer")
                            .preco(new BigDecimal("150.00"))
                            .ativo(true)
                            .build(),
                    Produto.builder()
                            .nome("Teclado Mecânico")
                            .preco(new BigDecimal("350.00"))
                            .ativo(true)
                            .build(),
                    Produto.builder()
                            .nome("Monitor 29")
                            .preco(new BigDecimal("1250.00"))
                            .ativo(true)
                            .build(),
                    Produto.builder()
                            .nome("Fone de ouvido bluetooth (descontinuado)")
                            .preco(new BigDecimal("200.00"))
                            .ativo(false)
                            .build()
            );
            repository.saveAll(produtosIniciais);
        }
    }
}

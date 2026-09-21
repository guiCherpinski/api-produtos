package br.com.aula.api_produtos.config;

import br.com.aula.api_produtos.entity.Produto;
import br.com.aula.api_produtos.entity.Usuario;
import br.com.aula.api_produtos.repository.ProdutoRepository;
import br.com.aula.api_produtos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

/** Carga inicial de dados para popular o banco durante a inicialização da aplicação */

@Configuration
public class CargaDadosInicial implements CommandLineRunner {
    private final ProdutoRepository repository;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CargaDadosInicial(ProdutoRepository repository, UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        if (userRepository.count() == 0) {
            List<Usuario> usuariosIniciais = List.of(
                    Usuario.builder()
                            .username("Eduardo supremo Geffert")
                            .password(passwordEncoder.encode("12345678"))
                            .role("ADMIN")
                            .build(),

                    Usuario.builder()
                            .username("cherpinski")
                            .password(passwordEncoder.encode("23456789"))
                            .role("CLIENTE")
                            .build()
            );
            userRepository.saveAll(usuariosIniciais);
        }
    }
}

package br.com.aula.api_produtos.repository;

import br.com.aula.api_produtos.entity.Usuario;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> Repositório da entidade usuario</p>
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}

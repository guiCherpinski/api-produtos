package br.com.aula.api_produtos.controller;

import br.com.aula.api_produtos.dto.DadosAutenticacao;
import br.com.aula.api_produtos.dto.DadosTokenJWT;
import br.com.aula.api_produtos.entity.Usuario;
import br.com.aula.api_produtos.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.Authenticator;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService service;

    public AutenticacaoController(AuthenticationManager manager, TokenService service){
        this.manager = manager;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao autenticacao){
        var authenticationToken = new UsernamePasswordAuthenticationToken(autenticacao.username(), autenticacao.password());
        var authentication = manager.authenticate(authenticationToken);

        String tokenJWT = service.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}

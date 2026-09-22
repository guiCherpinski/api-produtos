package br.com.aula.api_produtos.config;

import br.com.aula.api_produtos.repository.UsuarioRepository;
import br.com.aula.api_produtos.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;
    private final UsuarioRepository repository;

    public SecurityFilter(TokenService service, UsuarioRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            String subject = service.getSubject(tokenJWT);

            if (subject != null) {
                var usuario = repository.findByUsername(subject).orElse(null);
                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.replace("Bearer ","");
        }

        return null;
    }
}

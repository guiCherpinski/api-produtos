package br.com.aula.api_produtos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p> Essa classe é a classe de configuração da api </p>
 */

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/produtos", "/api/v1/produtos/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/produtos").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/produtos/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/produtos/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //@Bean
    //public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
    //    var userComum = User.builder()
    //            .username("cliente")
    //            .password(passwordEncoder.encode("123456"))
    //            .roles("user")
    //            .build();
//
    //    var userAdmin = User.builder()
    //            .username("admin")
    //            .password(passwordEncoder.encode("234567"))
    //            .roles("admin")
    //            .build();
//
    //    return new InMemoryUserDetailsManager(userComum,userAdmin);
    //}
}

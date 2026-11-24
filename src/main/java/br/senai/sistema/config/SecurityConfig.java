package br.senai.sistema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.seguranca.ativa:false}")
    private boolean ativa;

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {

        if (ativa) {
            http.authorizeHttpRequests(auth -> auth
                    // Arquivos públicos e páginas que não exigem login
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/login", "/diagnostico").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()

                    // Rotas estritamente restritas ao perfil ADMIN (Produtos, Categorias e Usuários)
                    .requestMatchers(
                            "/usuarios/**",
                            "/produtos/novo",
                            "/produtos/salvar",
                            "/produtos/editar/**",
                            "/produtos/*/editar",
                            "/produtos/excluir/**",
                            "/categorias/novo",
                            "/categorias/salvar",
                            "/categorias/editar/**",
                            "/categorias/*/editar",
                            "/categorias/excluir/**"
                    ).hasRole("ADMIN")

                    // Rotas partilhadas que ADMIN e OPERADOR podem acessar (Listagens, Painel, Movimentações)
                    .requestMatchers(
                            "/",
                            "/painel",
                            "/produtos",
                            "/produtos/detalhes/**",
                            "/categorias",
                            "/movimentacoes/**"
                    ).hasAnyRole("ADMIN", "OPERADOR")

                    // Todo o resto exige estar logado
                    .anyRequest().authenticated());
        } else {
            // Segurança desligada: tudo liberado
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        http
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/painel", true)
                    .permitAll())
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?saiu")
                    .permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
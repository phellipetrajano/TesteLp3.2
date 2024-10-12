package br.com.wavebox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Criação de um usuário em memória para teste
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) // Senha codificada
                .roles("USER")  // O papel do usuário
                .build());
        return manager;
    }

    // Criação de um encoder para codificar a senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usando o BCrypt para criptografar a senha
    }

    // Configuração das permissões e URLs protegidas/públicas
    @Bean
    public HttpSecurity securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Páginas públicas, que não precisam de login
                .antMatchers("/", "/login", "/registro").permitAll()  
                // Páginas privadas, que requerem login
                .antMatchers("/dashboard", "/pedidos", "/caixas").authenticated()
                // Qualquer outra página também precisa de autenticação
                .anyRequest().authenticated()
            .and()
            .formLogin()
                // Página personalizada de login
                .loginPage("/login") 
                // Página após login bem-sucedido
                .defaultSuccessUrl("/dashboard", true)  
                .permitAll()  // Permite acesso à página de login para todos
            .and()
            .logout()
                // Página após logout bem-sucedido
                .logoutSuccessUrl("/")  
                .permitAll();  // Permite acesso à página de logout para todos

        return http;  // Retorna a configuração do HttpSecurity
    }
}

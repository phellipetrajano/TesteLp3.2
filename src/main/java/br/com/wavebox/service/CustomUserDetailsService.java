package br.com.wavebox.service;

import br.com.wavebox.model.User;  // A classe User da sua entidade
import br.com.wavebox.repository.UserRepository;  // Repositório para buscar o usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;  // Para as roles do usuário
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Injeção do repositório para acessar o banco de dados

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar o usuário no banco de dados pelo nome de usuário
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // Se o usuário não for encontrado, lançar uma exceção
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        // Criar e retornar um UserDetails com as informações do usuário
        return new SecurityUser(
            user.getUsername(),   // Nome de usuário
            user.getPassword(),   // Senha codificada
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))  // Autoridade (role)
        );
    }
}

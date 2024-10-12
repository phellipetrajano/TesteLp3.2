package br.com.wavebox.repository;

import br.com.wavebox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    // Método para buscar o usuário pelo nome de usuário
    User findByUsername(String username);
}

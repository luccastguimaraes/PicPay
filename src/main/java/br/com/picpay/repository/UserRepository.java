package br.com.picpay.repository;


import br.com.picpay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
   Optional<T> findByDocument(String document);
}

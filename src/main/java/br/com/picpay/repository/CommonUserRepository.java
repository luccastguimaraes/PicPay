package com.picpaysimplificado.repository;

import com.picpaysimplificado.domain.user.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {
   @Override
   Optional<CommonUser> findById(Long aLong);

   Optional<CommonUser> findByDocument(String document);
}

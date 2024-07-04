package com.picpaysimplificado.repository;

import com.picpaysimplificado.domain.user.CommonUser;
import com.picpaysimplificado.domain.user.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {
   @Override
   Optional<MerchantUser> findById(Long aLong);

   Optional<MerchantUser> findByDocument(String document);

}

package br.com.picpay.repository;


import br.com.picpay.domain.user.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {
   @Override
   Optional<MerchantUser> findById(Long aLong);

   Optional<MerchantUser> findByDocument(String document);

}

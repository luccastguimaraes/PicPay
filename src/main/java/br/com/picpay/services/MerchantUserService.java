package br.com.picpay.services;

import br.com.picpay.domain.user.MerchantUser;
import br.com.picpay.repository.MerchantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantUserService extends UserService<MerchantUser> {

   @Autowired
   public MerchantUserService(MerchantUserRepository repository) {
      super(repository);
   }
}

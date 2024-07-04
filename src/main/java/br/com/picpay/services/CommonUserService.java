package br.com.picpay.services;

import br.com.picpay.domain.user.CommonUser;
import br.com.picpay.repository.CommonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CommonUserService extends UserService<CommonUser> {

   @Autowired
   public CommonUserService(CommonUserRepository repository) {
      super(repository);
   }
}

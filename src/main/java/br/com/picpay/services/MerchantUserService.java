package br.com.picpay.services;

import br.com.picpay.domain.user.MerchantUser;
import br.com.picpay.dto.UserDTO;
import br.com.picpay.repository.UserRepository;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MerchantUserService extends UserService<MerchantUser> {

   @Autowired
   public MerchantUserService(UserRepository<MerchantUser> repository) {
      super(repository);
   }

   @Override
   public MerchantUser findUserById(Long id) {
      return repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado com ID: " + id)
      );
   }


   @Override
   public MerchantUser createUser(UserDTO userDTO) {
      return new MerchantUser(
            userDTO.firstName(),
            userDTO.lastName(),
            userDTO.document(),
            userDTO.email(),
            userDTO.password(),
            userDTO.balance()
      );
   }
}

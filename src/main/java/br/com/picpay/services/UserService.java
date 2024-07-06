package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.dto.UserDTO;
import br.com.picpay.repository.UserRepository;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/*
   A ideia aqui é criar uma estrutura que permita funcionalidades comuns e
   específicas para cada tipo de User, permitindo reutilizar a mesma classe UserService
   , por meio de UserRepository desde que T seja uma subclass de User.
 */
public abstract class UserService<T extends User> {


   protected UserRepository<T> repository;

   public UserService(UserRepository<T>  repository) {
      this.repository = repository;
   }

   public T findUserById(Long id){
      return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
   }

   public void save(T user){
      try {
         this.repository.save(user);
      } catch (ConstraintViolationException e) {
         throw new IllegalArgumentException("Erro de validação ao salvar usuário: " + e.getMessage(), e);
      } catch (DataAccessException e) {
         throw new RuntimeException("Erro ao acessar dados ao salvar usuário: " + e.getMessage(), e);
      } catch (TransactionException e) {
         throw new RuntimeException("Erro na transação ao salvar usuário: " + e.getMessage(), e);
      }
   }

   public abstract T createUser(UserDTO userDTO);

   public List<T> getAllUsers() {
      return this.repository.findAll();
   }
}
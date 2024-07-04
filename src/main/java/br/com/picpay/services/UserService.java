package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
   A ideia aqui é criar uma estrutura que permita funcionalidades comuns e
   específicas para cada tipo de User, permitindo reutilizar a mesma classe UserService
   , por meio de diferentens implementações de repository desde T seja uma subclass de User.
 */
@Service
public abstract class UserService<T extends User> {

   protected final JpaRepository<T, Long> repository;

   // protected para garantir que seja instanciada apenas atraves de subclass concretas
   protected UserService(JpaRepository<T, Long> repository) {
      this.repository = repository;
   }

   @Transactional(readOnly = true)
   public T findById(Long id) {
      return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id invalid"));
   }

   @Transactional
   public T save(T user) {
      try {
         return this.repository.save(user);
      } catch (ConstraintViolationException e) {
         throw new IllegalArgumentException("Erro de validação ao salvar usuário: " + e.getMessage(), e);
      } catch (DataAccessException e) {
         throw new RuntimeException("Erro ao acessar dados ao salvar usuário: " + e.getMessage(), e);
      } catch (TransactionException e) {
         throw new RuntimeException("Erro na transação ao salvar usuário: " + e.getMessage(), e);
      }
   }


}
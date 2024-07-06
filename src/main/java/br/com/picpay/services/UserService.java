package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import br.com.picpay.repository.UserRepository;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   A ideia aqui é criar uma estrutura que permita funcionalidades comuns e
   específicas para cada tipo de User, permitindo reutilizar a mesma classe UserService
   , por meio de diferentens implementações de repository desde T seja uma subclass de User.
 */
public abstract class UserService<T extends User> {

   protected JpaRepository<T, Long> repository;

   // protected para garantir que seja instanciada apenas atraves de subclass concretas
   public UserService(UserRepository<T> repository) {
      this.repository = repository;
   }


   public abstract T findUserById(Long id);


   public abstract void save(T user);
}
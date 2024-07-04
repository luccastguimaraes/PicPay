package com.picpaysimplificado.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "common_users")
public class CommonUser extends User {

   public CommonUser(Long id, String firstName, String lastName, String document, String email, String password, BigDecimal balance) {
      super(id, firstName, lastName, document, email, password, balance, UserType.COMMON);
   }

}

package br.com.picpay.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "common_users")
public class CommonUser extends User {

   public CommonUser(String firstName, String lastName, String document, String email, String password, BigDecimal balance) {
      super(firstName, lastName, document, email, password, balance, UserType.COMMON);
   }

   public CommonUser() {
   }

   public void transfer(BigDecimal amountTransferred){
      this.balance = this.balance.subtract(amountTransferred);
   }
}

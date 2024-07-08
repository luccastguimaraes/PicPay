package br.com.picpay.domain.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "merchant_users")
public class MerchantUser extends User{

   public MerchantUser(String firstName, String lastName, String document, String email, String password, BigDecimal balance) {
      super(firstName, lastName, document, email, password, balance, UserType.MERCHANT);
   }

   public MerchantUser() {
      super(UserType.MERCHANT);
   }
}

package br.com.picpay.domain.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "merchant_users")
@Getter
@Setter
public class MerchantUser extends User{

   public MerchantUser(Long id, String firstName, String lastName, String document, String email, String password, BigDecimal balance) {
      super(id, firstName, lastName, document, email, password, balance, UserType.MERCHANT);
   }

}

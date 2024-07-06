package br.com.picpay.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@EqualsAndHashCode(of = "id")
public abstract class User {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   protected String firstName;
   protected String lastName;
   @Column(unique = true)
   protected String document;
   @Column(unique = true)
   protected String email;
   protected String password;
   protected BigDecimal balance;
   @Enumerated(EnumType.STRING)
   protected UserType userType;

   // protected para garantir que seja instanciada apenas atraves de subclass concretas
   protected User(String firstName, String lastName, String document, String email, String password, BigDecimal balance, UserType userType) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.document = document;
      this.email = email;
      this.password = password;
      this.balance = balance;
      this.userType = userType;
   }

   protected User() {
   }


   public void receive(BigDecimal amountTransferred) {
      this.balance = this.balance.add(amountTransferred);
   }

}

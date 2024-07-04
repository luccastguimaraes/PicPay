package br.com.picpay.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class User {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String firstName;
   private String lastName;
   @Column(unique = true)
   private String document;
   @Column(unique = true)
   private String email;
   private String password;
   private BigDecimal balance;
   @Enumerated(EnumType.STRING)
   private UserType userType;

   // protected para garantir que seja instanciada apenas atraves de subclass concretas
   protected User(Long id, String firstName, String lastName, String document, String email, String password, BigDecimal balance, UserType userType) {
      this.id = id;
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
}

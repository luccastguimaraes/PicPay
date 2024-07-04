package com.picpaysimplificado.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
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


}

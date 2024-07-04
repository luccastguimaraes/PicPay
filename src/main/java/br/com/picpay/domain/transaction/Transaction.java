package br.com.picpay.domain.transaction;


import br.com.picpay.domain.user.CommonUser;
import br.com.picpay.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private BigDecimal amount;
   @ManyToOne
   @JoinColumn(name = "payer_id")
   private CommonUser payer;
   @ManyToOne
   @JoinColumn(name = "payee_id")
   private User payee;
   private LocalDateTime transactionTime;

}
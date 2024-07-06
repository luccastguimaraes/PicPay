package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.user.CommonUser;
import br.com.picpay.domain.user.User;
import br.com.picpay.dto.AuthorizationResponse;
import br.com.picpay.dto.TransactionDTO;
import br.com.picpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService<T extends User> {

   private UserService<T> userService;
   private TransactionRepository repository;
   private RestTemplate restTemplate;
   @Value("${mock.autorizador}")
   private String mockAutorizadorUrl;

   @Autowired
   public TransactionService(UserService<T> userService, TransactionRepository repository, RestTemplate restTemplate) {
      this.userService = userService;
      this.repository = repository;
      this.restTemplate = restTemplate;
   }

   public void startTransaction(TransactionDTO transactionDTO) throws Exception {
      User payerUser = this.userService.findUserById(transactionDTO.payerId());
      CommonUser payer = validatePayer(payerUser);
      BigDecimal amountTransferred = transactionDTO.amountTransferred();
      validatePayerBalancer(payer, amountTransferred);
      User payee = this.userService.findUserById(transactionDTO.payeeId());
      externalAuthorizer(payer, payee, amountTransferred);


   }

   private void validatePayerBalancer(CommonUser payer, BigDecimal amountTransferred) throws Exception {
      if (this.userService instanceof CommonUserService commonUserService) {
         commonUserService.validateBalance(payer, amountTransferred);
      } else {
         throw new IllegalStateException("userService não é uma instância de CommonUserService");
      }

   }

   private CommonUser validatePayer(User payerUser) {
      if (payerUser instanceof CommonUser) {
         return (CommonUser) payerUser;
      } else {
         throw new IllegalArgumentException("Tipo de usuário não suportado para transações.");
      }
   }

   private void externalAuthorizer(CommonUser payer, User payee, BigDecimal amountTransferred) throws Exception {
      AuthorizationResponse authorizationResponse = restTemplate.getForObject(
            this.mockAutorizadorUrl,
            AuthorizationResponse.class
      );
      boolean criterion1 = false;
      boolean criterion2 = false;
      if (authorizationResponse!=null) {
         criterion1 = "success".equalsIgnoreCase(authorizationResponse.status());
         criterion2 = authorizationResponse.data().authorization();
         if (criterion1 && criterion2) {
            this.createTransaction(payer, payee, amountTransferred);
         } else {
            throw new Exception("Authorization is false");
         }
      } else {
         throw new IllegalArgumentException("External Authorization Response is null");
      }
   }

   private void createTransaction(CommonUser payer, User payee, BigDecimal amountTransferred) {
      try {
         Transaction transaction = new Transaction();
         transaction.setPayer(payer);
         transaction.setPayee(payee);
         transaction.setAmount(amountTransferred);
         transaction.setTransactionTime(LocalDateTime.now());
         payer.transfer(amountTransferred);
         payee.receive(amountTransferred);
         repository.save(transaction);
         userService.save((T) payer);
         userService.save((T) payee);

      } catch (Exception e) {
         System.out.println("Erro ao criar transação: " + e.getMessage());
      }

   }


}

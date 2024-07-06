package br.com.picpay.services;

import br.com.picpay.domain.transaction.Transaction;
import br.com.picpay.domain.user.CommonUser;
import br.com.picpay.domain.user.MerchantUser;
import br.com.picpay.domain.user.User;
import br.com.picpay.dto.AuthorizationResponse;
import br.com.picpay.dto.TransactionDTO;
import br.com.picpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

   private CommonUserService commonUserService;
   private UserService<User> userService;
   private TransactionRepository repository;
   private RestTemplate restTemplate;
   @Value("${mock.autorizador}")
   private String mockAutorizadorUrl;

   @Autowired
   public TransactionService(CommonUserService commonUserService, UserService<User> userService, TransactionRepository repository, RestTemplate restTemplate) {
      this.commonUserService = commonUserService;
      this.userService = userService;
      this.repository = repository;
      this.restTemplate = restTemplate;
   }

   /*
      Para que todas as operações dentro deste método devem ser tratadas como uma transação única.
      Se ocorrer uma exceção (Exception ou subclasse dela),
      a transação será revertida (rollback) automaticamente, garantindo a integridade dos dados.
    */
   @Transactional(rollbackFor = Exception.class)
   public void startTransaction(TransactionDTO transactionDTO) throws Exception {
      CommonUser payer = validatePayer(transactionDTO.payerId());
      BigDecimal amountTransferred = transactionDTO.amountTransferred();
      validatePayerBalancer(payer, amountTransferred);
      var payee = this.userService.findUserById(transactionDTO.payeeId());
      externalAuthorizer(payer, payee, amountTransferred);


   }

   private void validatePayerBalancer(CommonUser payer, BigDecimal amountTransferred) throws Exception {
      this.commonUserService.validateBalance(payer, amountTransferred);
   }

   private CommonUser validatePayer(Long id) {
      User payerUser = this.userService.findUserById(id);
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
         criterion2 = "true".equalsIgnoreCase(String.valueOf(authorizationResponse.data().authorization()));
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
         commonUserService.save(payer);
         if (payee instanceof CommonUser commonUserPayee){
            userService.save(commonUserPayee);
         } else if (payee instanceof MerchantUser merchantUserPayee) {
            userService.save(merchantUserPayee);
         }

      } catch (Exception e) {
         System.out.println("Erro ao criar transação: " + e.getMessage());
      }

   }


}

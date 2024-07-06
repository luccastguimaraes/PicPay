package br.com.picpay.controller;

import br.com.picpay.dto.TransactionDTO;
import br.com.picpay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

   private TransactionService transactionService;

   @Autowired
   public TransactionController(TransactionService transactionService) {
      this.transactionService = transactionService;
   }

   @PostMapping("/transfer")
   public void createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) throws Exception {
      this.transactionService.startTransaction(transactionDTO);
   }
}

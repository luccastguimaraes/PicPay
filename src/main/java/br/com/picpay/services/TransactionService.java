package br.com.picpay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

   private final CommonUserService commonUserService;
   private final MerchantUserService merchantUserService;

   @Autowired
   public TransactionService(CommonUserService commonUserService, MerchantUserService merchantUserService) {
      this.commonUserService = commonUserService;
      this.merchantUserService = merchantUserService;
   }
}

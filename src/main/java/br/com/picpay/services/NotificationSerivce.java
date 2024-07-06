package br.com.picpay.services;

import br.com.picpay.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationSerivce {

   private RestTemplate restTemplate;
   private final String mockSendNotification;

   @Autowired
   public NotificationSerivce(RestTemplate restTemplate, @Value("${mock.send.notification}") String mockSendNotification) {
      this.restTemplate = restTemplate;
      this.mockSendNotification = mockSendNotification;
   }

   public void sendNotification(User payee, String msg) throws Exception {
      try{
         String email = payee.getEmail();
         NotificationRequest noficationResquest = new NotificationRequest(email, msg);
         ResponseEntity<String> notificationResponse = restTemplate.postForEntity(mockSendNotification, noficationResquest, String.class);
         if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            throw new Exception("NotificationService está indisponível/instável");
         }

      } catch (Exception e) {
         throw new Exception("Erro sendNotification");
      }
   }
}

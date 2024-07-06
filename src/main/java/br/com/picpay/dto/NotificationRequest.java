package br.com.picpay.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(@NotBlank String email, String status, NotificationData data) {
   public record NotificationData(String message){}
}

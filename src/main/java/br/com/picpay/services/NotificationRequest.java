package br.com.picpay.services;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(@NotBlank String email, @NotBlank String msg) {
}

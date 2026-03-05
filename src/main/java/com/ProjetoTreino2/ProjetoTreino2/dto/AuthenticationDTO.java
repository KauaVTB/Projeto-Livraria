package com.ProjetoTreino2.ProjetoTreino2.dto;

public record AuthenticationDTO(String login, String password) {
    public AuthenticationDTO {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}

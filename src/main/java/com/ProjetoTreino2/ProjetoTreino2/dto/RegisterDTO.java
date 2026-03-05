package com.ProjetoTreino2.ProjetoTreino2.dto;

import com.ProjetoTreino2.ProjetoTreino2.Entities.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    public RegisterDTO {

    }

}

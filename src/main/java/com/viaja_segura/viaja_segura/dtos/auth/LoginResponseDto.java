package com.viaja_segura.viaja_segura.dtos.auth;

public class LoginResponseDto {
    public String token;
    private String role;
    private Long id;

    public LoginResponseDto(String token, String role, Long id) {
        this.token = token;
        this.role = role;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }
}

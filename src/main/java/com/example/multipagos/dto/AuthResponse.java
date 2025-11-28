package com.example.multipagos.dto;

import java.util.List;

public class AuthResponse {

    public String token;
    public String username;
    public Long companyActive;
    public String role;

    public AuthResponse(String token, String username, Long companyActive, String role) {
        this.token = token;
        this.username = username;
        this.companyActive = companyActive;
        this.role = role;
    }
}


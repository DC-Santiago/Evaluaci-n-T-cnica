package com.evaluacion_back.permisos.dto;

/**
 *
 * @author SantiagoDC
 */

public class AuthResponse {

    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
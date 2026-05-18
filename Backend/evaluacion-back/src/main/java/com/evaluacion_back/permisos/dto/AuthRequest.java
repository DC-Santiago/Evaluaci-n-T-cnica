package com.evaluacion_back.permisos.dto;

/**
 *
 * @author SantiagoDC
 */

public class AuthRequest {

    private String correo;
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getUsername() {
        return correo;
    }

    public void setUsername(String username) {
        this.correo = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package com.evaluacion_back.permisos.dto;

/**
 *
 * @author SantiagoDC
 */
public class AuthRequest {

    private String correo;
    private String contrasena; // 🔹 Cambiado de 'password' a 'contrasena' para coincidir al 100% con el JSON del Front

    public AuthRequest() {
    }

    public AuthRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // 🔹 Cambiado a getCorreo() para que sea semántico
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // 🔹 Cambiado a getContrasena() para acoplarse al servicio del Frontend
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
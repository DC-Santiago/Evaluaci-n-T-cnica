package com.evaluacion_back.permisos.controller;

/**
 *
 * @author SantiagoDC
 */

import com.evaluacion_back.permisos.config.JwtUtil;
import com.evaluacion_back.permisos.dto.AuthRequest;
import com.evaluacion_back.permisos.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
// 🔹 PASO 1: Habilitamos el acceso explícito a tu Frontend de Angular en el puerto 4200
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        // 🔹 PASO 2: Ajustamos para usar los getters de tu DTO real (correo y contrasena)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),      // 👈 Cambiado de getUsername()
                        request.getContrasena()  // 👈 Cambiado de getPassword()
                )
        );

        // Generamos el token amarrado al identificador único (su correo electrónico)
        String token = jwtUtil.generateToken(request.getCorreo()); // 👈 Cambiado de getUsername()

        return new AuthResponse(token);
    }
}
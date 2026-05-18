package com.evaluacion_back.permisos.controller;

import com.evaluacion_back.permisos.entity.Sistema;
import com.evaluacion_back.permisos.repository.SistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sistemas")
public class SistemaController {

    @Autowired
    private SistemaRepository sistemaRepository;

    // Listar todos los sistemas
    @GetMapping
    public List<Sistema> listar() {
        return sistemaRepository.findAll();
    }

    // Crear un nuevo sistema
    @PostMapping
    public Sistema crear(@RequestBody Sistema sistema) {
        return sistemaRepository.save(sistema);
    }
}
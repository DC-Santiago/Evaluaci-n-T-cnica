package com.evaluacion_back.permisos.controller;

/**
 *
 * @author SantiagoDC
 */

import com.evaluacion_back.permisos.entity.Modulo;
import com.evaluacion_back.permisos.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    @Autowired
    private ModuloRepository moduloRepository;

    // 🔹 GET: Listar todos los módulos
    @GetMapping
    public List<Modulo> listar() {
        return moduloRepository.findAll();
    }

    // 🔹 POST: Crear un nuevo módulo
    @PostMapping
    public Modulo crear(@RequestBody Modulo modulo) {
        return moduloRepository.save(modulo);
    }
}
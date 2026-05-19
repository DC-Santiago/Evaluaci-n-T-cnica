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
// 🔹 Permitimos la manipulación de módulos desde la aplicación Angular
@CrossOrigin(origins = "http://localhost:4200")
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


    @PutMapping("/{id}")
    public Modulo actualizar(@PathVariable Integer id, @RequestBody Modulo moduloDetalles) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado con ID: " + id));
        
        modulo.setNombre(moduloDetalles.getNombre());
        modulo.setSistema(moduloDetalles.getSistema());
        
        return moduloRepository.save(modulo);
    }
}
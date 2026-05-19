package com.evaluacion_back.permisos.controller;

import com.evaluacion_back.permisos.entity.Sistema;
import com.evaluacion_back.permisos.repository.SistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sistemas")
// 🔹 Permitimos que la pantalla de administración de sistemas lea y guarde datos
@CrossOrigin(origins = "http://localhost:4200")
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


    @PutMapping("/{id}")
    public Sistema actualizar(@PathVariable Integer id, @RequestBody Sistema sistemaDetalles) {
        Sistema sistema = sistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sistema no encontrado con ID: " + id));
        
        sistema.setNombre(sistemaDetalles.getNombre());
        
        return sistemaRepository.save(sistema);
    }
}
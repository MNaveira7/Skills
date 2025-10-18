package com.fct.clubs.controller;


import com.fct.clubs.entities.RolEntity;
import com.fct.clubs.repository.RolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
@Tag(name = "Rol", description = "Endpoints necesarios para la entidad de rol")
public class RolController {

    private final RolRepository roleRepository;

    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<RolEntity>> obtenerTodosLosRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @Operation(summary = "Obtener por id")
    @GetMapping("/{idRol}")
    public ResponseEntity<RolEntity> obtenerRolPorId(@PathVariable Integer idRol) {
        RolEntity role = roleRepository.findById(idRol).orElse(null);
        if (Objects.isNull(role)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<RolEntity> crearRol(@RequestBody RolEntity rolEntity) {
        RolEntity saved = roleRepository.save(rolEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar por id")
    @PutMapping("/{rolId}")
    public ResponseEntity<RolEntity> actualizarRol(@PathVariable Integer rolId,
                                                    @RequestBody RolEntity body) {
        RolEntity role = roleRepository.findById(rolId).orElse(null);
        if (Objects.isNull(role)) {
            return ResponseEntity.notFound().build();
        }

        role.setNombreRol(body.getNombreRol());

        RolEntity updated = roleRepository.save(role);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{rolId}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer rolId) {
        if (Boolean.FALSE.equals(roleRepository.existsById(rolId))) {
            return ResponseEntity.notFound().build();
        }
        roleRepository.deleteById(rolId);
        return ResponseEntity.noContent().build();
    }

}

package com.fct.clubs.controller;

import com.fct.clubs.dto.UsuarioRequest;
import com.fct.clubs.dto.UsuarioResponse;
import com.fct.clubs.entities.RolEntity;
import com.fct.clubs.entities.UsuarioEntity;
import com.fct.clubs.mapper.UsuarioMapper;
import com.fct.clubs.repository.RolRepository;
import com.fct.clubs.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Endpoints necesarios para la entidad de usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerTodosLosUsuarios() {
        List<UsuarioResponse> response = usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener por id")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(@PathVariable Integer idUsuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario).orElse(null);
        if (Objects.isNull(usuarioEntity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioMapper.fromEntityToDTO(usuarioEntity));
    }

    @Operation(summary = "Crear (valida que idRol exista)")
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        if (Objects.isNull(usuarioRequest) || Objects.isNull(usuarioRequest.getRolId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'rolId' es obligatorio.");
        }

        RolEntity rol = rolRepository.findById(usuarioRequest.getRolId()).orElse(null);
        if (Objects.isNull(rol)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El rol con id=" + usuarioRequest.getRolId() + " no existe.");
        }

        UsuarioEntity toSave = usuarioMapper.fromDtoRequestToEntity(usuarioRequest);

        if (!Objects.isNull(usuarioRequest.getContraseña())) {
            toSave.setContraseña(passwordEncoder.encode(usuarioRequest.getContraseña()));
        }

        toSave.setRol(rol);

        UsuarioEntity saved = usuarioRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar por id (si llega rolId, valida y actualiza)")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Integer idUsuario,
                                                             @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (Objects.isNull(usuario)) {
            return ResponseEntity.notFound().build();
        }

        if (!Objects.isNull(usuarioRequest.getRolId())) {
            RolEntity nuevoRol = rolRepository.findById(usuarioRequest.getRolId()).orElse(null);
            if (Objects.isNull(nuevoRol)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El rol con id=" + usuarioRequest.getRolId() + " no existe.");
            }
            usuario.setRol(nuevoRol);
        }

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setEmail(usuarioRequest.getEmail());

        if (!Objects.isNull(usuarioRequest.getContraseña())) {
            usuario.setContraseña(passwordEncoder.encode(usuarioRequest.getContraseña()));
        }

        UsuarioEntity updated = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer idUsuario) {
        if (Boolean.FALSE.equals(usuarioRepository.existsById(idUsuario))) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
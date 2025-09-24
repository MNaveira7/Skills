package com.fct.clubs.controller;

import com.fct.clubs.entities.LocalizacionEntity;
import com.fct.clubs.repository.LocalizacionRepository;
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
@RequestMapping("/localizaciones")
@Tag(name = "Localizacion", description = "Endpoints necesarios para la entidad de localización")
public class LocalizacionController {

    private final LocalizacionRepository localizacionRepository;

    @Operation(summary = "Listar todas")
    @GetMapping
    public ResponseEntity<List<LocalizacionEntity>> ontenerTodasLasLocalizaciones() {
        return ResponseEntity.ok(localizacionRepository.findAll());
    }

    @Operation(summary = "Obtener por id")
    @GetMapping("/{idLocalizacion}")
    public ResponseEntity<LocalizacionEntity> obtenerLocalizacionByLocalizacionId(@PathVariable Integer idLocalizacion) {
        LocalizacionEntity localizacionEntity = localizacionRepository.findById(idLocalizacion).orElse(null);
        if (Objects.isNull(localizacionEntity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(localizacionEntity);
    }

    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<LocalizacionEntity> añadirLocalizacion(@RequestBody LocalizacionEntity localizacionEntity) {
        LocalizacionEntity saved = localizacionRepository.save(localizacionEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idLocalizacion}")
    public ResponseEntity<LocalizacionEntity> actualizarLocalizacion(@PathVariable Integer idLocalizacion,
                                                     @RequestBody LocalizacionEntity body) {
        LocalizacionEntity localizacionEntity = localizacionRepository.findById(idLocalizacion).orElse(null);
        if (Objects.isNull(localizacionEntity)) {
            return ResponseEntity.notFound().build();
        }

        localizacionEntity.setProvincia(body.getProvincia());
        localizacionEntity.setMunicipio(body.getMunicipio());
        localizacionEntity.setCodigoPostal(body.getCodigoPostal());
        localizacionEntity.setDireccion(body.getDireccion());
        localizacionEntity.setLatitud(body.getLatitud());
        localizacionEntity.setLongitud(body.getLongitud());

        LocalizacionEntity updated = localizacionRepository.save(localizacionEntity);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idLocalizacion}")
    public ResponseEntity<Void> eliminarLocalizacion(@PathVariable Integer idLocalizacion) {
        if (Boolean.FALSE.equals(localizacionRepository.existsById(idLocalizacion))) {
            return ResponseEntity.notFound().build();
        }
        localizacionRepository.deleteById(idLocalizacion);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar por provincia y/o municipio")
    @GetMapping("/buscar")
    public ResponseEntity<List<LocalizacionEntity>> buscarLocalizacionPorProvinciaMunicipio(
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String municipio) {

        List<LocalizacionEntity> resultados =
                localizacionRepository.findByProvinciaAndMunicipio(provincia, municipio);
        return ResponseEntity.ok(resultados);
    }
}

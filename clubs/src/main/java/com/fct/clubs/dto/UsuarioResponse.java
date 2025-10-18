package com.fct.clubs.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer usuarioId;
    private String nombre;
    private String email;
    private Integer rolId;
    private String nombreRol;
}

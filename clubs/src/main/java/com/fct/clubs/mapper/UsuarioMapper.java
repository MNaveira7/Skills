package com.fct.clubs.mapper;

import com.fct.clubs.dto.UsuarioRequest;
import com.fct.clubs.dto.UsuarioResponse;
import com.fct.clubs.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // SOURCE -> TARGET (DTO -> Entity)
    @Mapping(source = "nombre",target = "nombre")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "contraseña",target = "contraseña")
    UsuarioEntity fromDtoRequestToEntity(UsuarioRequest usuarioRequest);

    // TARGET -> DTO (Entity -> DTO)
    @Mapping(source = "usuarioId",target = "usuarioId")
    @Mapping(source = "nombre",target = "nombre")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "rol.rolId",target = "rolId")
    @Mapping(source = "rol.nombreRol",target = "nombreRol")
    UsuarioResponse fromEntityToDTO(UsuarioEntity usuarioEntity);
}

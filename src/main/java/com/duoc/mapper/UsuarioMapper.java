package com.duoc.mapper;

import com.duoc.dto.UsuarioDTO;
import com.duoc.enums.UserRole;
import com.duoc.model.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO usuarioEntityToUsuarioDto(UsuarioEntity usuarioEntity) {
        return UsuarioDTO.builder()
                .id(usuarioEntity.getId())
                .username(usuarioEntity.getUsername())
                .password(usuarioEntity.getPassword())
                .email(usuarioEntity.getEmail())
                .role(usuarioEntity.getRole())
                .nombre(usuarioEntity.getNombre())
                .apellidoPaterno(usuarioEntity.getApellidoPaterno())
                .edad(usuarioEntity.getEdad())
                .fechaNacimiento(usuarioEntity.getFechaNacimiento())
                .build();
    }

    public UsuarioEntity usuarioDtoToUsuarioEntity(UsuarioDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .username(usuarioDTO.getUsername())
                .password(usuarioDTO.getPassword())
                .email(usuarioDTO.getEmail())
                .role(usuarioDTO.getRole())
                .nombre(usuarioDTO.getNombre())
                .apellidoPaterno(usuarioDTO.getApellidoPaterno())
                .edad(usuarioDTO.getEdad())
                .fechaNacimiento(usuarioDTO.getFechaNacimiento())
                .build();
    }
 }

package com.duoc.controllers;

import com.duoc.dto.ActualizarContrasenaDTO;
import com.duoc.dto.EliminarUsuarioDTO;
import com.duoc.dto.UsuarioDTO;
import com.duoc.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    // Obtener todos los usuarios registrados
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.getUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    // Obtener usuario por su id
    @GetMapping("/{id-usuario}")
    public ResponseEntity<UsuarioDTO> getUsuariosById(@PathVariable("id-usuario") Long idUsuario) {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioById(idUsuario);

        return ResponseEntity.ok(usuarioDTO);
    }

    // Crear un usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuarioDTO);

        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> modificarUsuario(
            @RequestBody UsuarioDTO nuevoUsuarioDTO
    ) {
        UsuarioDTO usuarioDTOModificado = usuarioService.modificarUsuario(nuevoUsuarioDTO);

        return new ResponseEntity<>(usuarioDTOModificado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<EliminarUsuarioDTO> eliminarUsuarioById(@PathVariable("id-usuario") Long idUsuario) {
        usuarioService.eliminarUsuarioById(idUsuario);
        EliminarUsuarioDTO eliminarLibroDTO = new EliminarUsuarioDTO("Usuario eliminado exitosamente con ID: " + idUsuario);

        return ResponseEntity.ok(eliminarLibroDTO);
    }

    @GetMapping("/existe/{id-usuario}")
    public ResponseEntity<Boolean> usuarioExiste(@PathVariable("id-usuario") Long idUsuario) {
        boolean existe = usuarioService.usuarioExiste(idUsuario);
        return ResponseEntity.ok(existe);
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<UsuarioDTO> cambiarContrasena(@RequestBody ActualizarContrasenaDTO dto) {
        UsuarioDTO usuarioActualizado = usuarioService.cambiarContrasena(dto.getEmail(), dto.getNuevaContrasena());
        return ResponseEntity.ok(usuarioActualizado);
    }

}

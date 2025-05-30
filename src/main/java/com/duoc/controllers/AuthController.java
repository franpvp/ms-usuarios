package com.duoc.controllers;

import com.duoc.dto.LoginDTO;
import com.duoc.dto.LoginResponseDTO;
import com.duoc.dto.LogoutResponseDTO;
import com.duoc.exceptions.UsuarioNotFoundException;
import com.duoc.model.UsuarioEntity;
import com.duoc.repositories.UsuarioRepository;
import com.duoc.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(usuarioService.login(loginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(
            @RequestParam("username") String username
    ) {
        return ResponseEntity.ok(usuarioService.logout(username));
    }

    @GetMapping("/estado/{id-usuario}")
    public ResponseEntity<Boolean> verificarEstadoUsuario(@PathVariable("id-usuario") Long idUsuario) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario.isLoggedIn());  // Devuelve si el usuario está logueado
    }
}

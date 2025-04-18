package com.duoc.services;

import com.duoc.dto.LoginDTO;
import com.duoc.dto.LoginResponseDTO;
import com.duoc.dto.LogoutResponseDTO;
import com.duoc.dto.UsuarioDTO;
import com.duoc.enums.UserRole;
import com.duoc.exceptions.*;
import com.duoc.mapper.UsuarioMapper;
import com.duoc.model.UsuarioEntity;
import com.duoc.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> getUsuarios() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();

        if(usuarios.isEmpty()) {
            throw new UsuarioNotFoundException("No hay usuarios registrados");
        }
        // Se mapean los objetos usuarios a Entity a DTO
        return usuarios.stream()
                .map(usuarioMapper::usuarioEntityToUsuarioDto)
                .toList();
    }

    @Override
    public UsuarioDTO getUsuarioById(Long id) {

        if(id <= 0) {
            throw new IllegalNumberException("Se debe ingresar un número positivo");
        }
        return usuarioRepository.findById(id)
                .map(usuarioMapper::usuarioEntityToUsuarioDto)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {

        if (usuarioDTO.getId() != null && usuarioDTO.getId() <= 0) {
            throw new IllegalNumberException("El ID del usuario debe ser positivo y no nulo");
        }

        // Verificar si el nombre de usuario ya existe
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new UsuarioDuplicadoException("El nombre de usuario '" + usuarioDTO.getUsername() + "' ya está en uso.");
        }

        if (usuarioDTO.getRole() == null) {
            usuarioDTO.setRole(UserRole.USER);
        }

        UsuarioEntity usuarioEntity = usuarioMapper.usuarioDtoToUsuarioEntity(usuarioDTO);
        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);

        return usuarioMapper.usuarioEntityToUsuarioDto(usuarioGuardado);
    }

    @Override
    public UsuarioDTO modificarUsuario(UsuarioDTO nuevoUsuarioDTO) {

        return usuarioRepository.findById(nuevoUsuarioDTO.getId())
                .map(usuarioEntity -> {
                    usuarioEntity.setUsername(nuevoUsuarioDTO.getUsername());
                    usuarioEntity.setEmail(nuevoUsuarioDTO.getEmail());
                    usuarioEntity.setNombre(nuevoUsuarioDTO.getNombre());
                    usuarioEntity.setApellidoPaterno(nuevoUsuarioDTO.getApellidoPaterno());
                    usuarioEntity.setEdad(nuevoUsuarioDTO.getEdad());
                    usuarioEntity.setFechaNacimiento(nuevoUsuarioDTO.getFechaNacimiento());

                    UsuarioEntity usuarioEntityModificado = usuarioRepository.save(usuarioEntity);
                    return usuarioMapper.usuarioEntityToUsuarioDto(usuarioEntityModificado);
                })
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con ID: " + nuevoUsuarioDTO.getId()));
    }

    @Override
    public void eliminarUsuarioById(Long id) {

        if (id < 0) {
            throw new UsuarioBadRequestException("El ID del usuario no puede ser negativo: " + id);
        }

        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }


    // Validar si el usuario existe
    public boolean usuarioExiste(Long idUsuario) {
        return usuarioRepository.existsById(idUsuario);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        usuario.setLoggedIn(true);
        usuarioRepository.save(usuario);

        String token = generarTokenSimulado(usuario);

        return new LoginResponseDTO(token, usuario.getUsername(), usuario.getId(), usuario.getRole().name());
    }



    private String generarTokenSimulado(UsuarioEntity usuario) {
        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String payload = Base64.getEncoder().encodeToString((
                "{\"sub\":\"" + usuario.getUsername() + "\",\"id\":" + usuario.getId() + "}"
        ).getBytes());
        String signature = Base64.getEncoder().encodeToString("mi-secreto".getBytes()); // simulación de firma

        return header + "." + payload + "." + signature;
    }


    public LogoutResponseDTO logout(String username) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        usuario.setLoggedIn(false);
        usuarioRepository.save(usuario);

        String mensaje =  "Usuario " + username + " ha cerrado sesión.";
        return new LogoutResponseDTO(mensaje);
    }

    @Override
    public UsuarioDTO cambiarContrasena(String email, String nuevaContrasena) {
        // Buscar usuario por email
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con email: " + email));

        // Actualizar la contraseña
        usuario.setPassword(nuevaContrasena);

        // Guardar y mapear el usuario actualizado
        UsuarioEntity usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.usuarioEntityToUsuarioDto(usuarioActualizado);
    }

}

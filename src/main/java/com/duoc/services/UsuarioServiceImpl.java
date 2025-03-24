package com.duoc.services;

import com.duoc.dto.LoginDTO;
import com.duoc.dto.UsuarioDTO;
import com.duoc.enums.UserRole;
import com.duoc.exceptions.*;
import com.duoc.mapper.UsuarioMapper;
import com.duoc.model.UsuarioEntity;
import com.duoc.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public String login(LoginDTO loginDTO) {
        // Buscar usuario por username
        UsuarioEntity usuario = usuarioRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Verificar si la contraseña coincide (sin encriptación)
        if (!usuario.getPassword().equals(loginDTO.getPassword())) {
            throw new PasswordException("Contraseña incorrecta");
        }

        // Actualizar estado de sesión
        usuario.setLoggedIn(true);
        usuarioRepository.save(usuario);

        return "Usuario " + usuario.getUsername() + " ha iniciado sesión exitosamente.";
    }

    public String logout(String username) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        usuario.setLoggedIn(false);
        usuarioRepository.save(usuario);

        return "Usuario " + username + " ha cerrado sesión.";
    }

}

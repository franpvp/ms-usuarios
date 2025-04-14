package com.duoc.services;


import com.duoc.dto.LoginDTO;
import com.duoc.dto.LoginResponseDTO;
import com.duoc.dto.LogoutResponseDTO;
import com.duoc.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {

    List<UsuarioDTO> getUsuarios();
    UsuarioDTO getUsuarioById(Long id);
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO modificarUsuario(UsuarioDTO usuarioDTO);
    void eliminarUsuarioById(Long id);
    boolean usuarioExiste(Long idUsuario);
    LoginResponseDTO login(LoginDTO loginDTO);
    LogoutResponseDTO logout(String username);
    UsuarioDTO cambiarContrasena(String email, String nuevaContrasena);

}

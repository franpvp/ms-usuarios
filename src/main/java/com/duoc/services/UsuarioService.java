package com.duoc.services;


import com.duoc.dto.LoginDTO;
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
    String login(LoginDTO loginDTO);
    String logout(String username);

}

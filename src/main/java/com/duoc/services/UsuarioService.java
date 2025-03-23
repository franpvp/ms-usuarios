package com.duoc.services;


import com.duoc.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {

    List<UsuarioDTO> getUsuarios();
    UsuarioDTO getUsuarioById(Long id);
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO);
    void eliminarUsuarioById(Long id);
    // UserDetails loadUserByUsername(String username);
    boolean usuarioExiste(Long idUsuario);

}

package com.duoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarContrasenaDTO {
    private String email;
    private String nuevaContrasena;
}

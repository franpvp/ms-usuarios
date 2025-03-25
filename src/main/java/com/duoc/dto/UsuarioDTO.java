package com.duoc.dto;

import com.duoc.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotNull(message = "El campo id no puede estar vacío")
    @Positive(message = "El campo id debe ser un número positivo")
    private Long id;

    @NotNull(message = "El campo username no puede estar vacío")
    @Size(min = 2, max = 10, message = "El campo nombre debe tener entre 2 y 10 caracteres")
    private String username;

    @NotNull(message = "El campo password no puede estar vacío")
    @Size(min = 6, max = 16, message = "El campo password debe tener entre 6 y 16 caracteres")
    private String password;

    @NotNull(message = "El campo email no puede estar vacío")
    private String email;

    @NotNull(message = "El campo role no puede estar vacío")
    private UserRole role;

    @NotNull(message = "El campo nombre no puede estar vacío")
    @Size(min = 2, max = 20, message = "El campo nombre debe tener entre 6 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El campo nombre solo puede contener letras")
    private String nombre;

    @NotNull(message = "El campo apellidoPaterno no puede estar vacío")
    @Size(min = 2, max = 20, message = "El campo nombre debe tener entre 2 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El campo apellidoPaterno solo puede contener letras")
    private String apellidoPaterno;

    @NotNull(message = "El campo edad no puede estar vacío")
    @Positive(message = "El campo edad debe ser un número positivo")
    private Integer edad;

    @NotNull(message = "El campo fechaNacimiento no puede estar vacío")
    private Date fechaNacimiento;

    @NotNull(message = "El campo isLoggedIn no puede estar vacío")
    private boolean isLoggedIn = false;
}

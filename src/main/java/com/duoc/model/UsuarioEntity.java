package com.duoc.model;

import com.duoc.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(nullable = false)
    private UserRole role;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "is_logged_in")
    private boolean isLoggedIn = false;

    // Métodos helper para verificar roles
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isModerator() {
        return role == UserRole.MODERATOR;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }

    public boolean canModerate() {
        return isAdmin() || isModerator();
    }
}

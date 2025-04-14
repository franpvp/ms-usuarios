package com.duoc.dto;



import com.duoc.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String username;
    private Long userId;
    private String role;
}

package com.cs206.g2t2.data.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements UserRequest{

    @NotBlank(message = "Username is required")
    @Size(min = 8,
            max = 30,
            message = "Username must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,
            max = 120,
            message = "Password must be between {min} and {max} characters long")
    private String password;
}

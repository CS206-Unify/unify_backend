package com.cs206.g2t2.data.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    // username stores the username to be authenticated with the repository
    @NotBlank(message = "Username is required")
    @Size(min = 8,
            max = 30,
            message = "Username must be between {min} and {max} characters long")
    private String username;

    // password stores the password to be authenticated with the repository
    @NotBlank(message = "Password is required")
    @Size(min = 8,
            max = 120,
            message = "Password must be between {min} and {max} characters long")
    private String password;

}

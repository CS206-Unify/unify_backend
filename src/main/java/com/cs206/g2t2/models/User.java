package com.cs206.g2t2.models;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document("user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails { // Implements UserDetails so that the security.core library can be used

    private String _id;

    private LocalDateTime userCreationDate;

    @NotBlank(message = "Username is required")
    @Size(min = 8,
          max = 30,
          message = "Username must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
           message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,
          max = 120,
          message = "Password must be between {min} and {max} characters long")
    private String password;

    private String imageString;

    private List<String> languages;

    private BSProfile bsProfile;

    private List<String> teams;

    // Methods to extend UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> "USER");
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

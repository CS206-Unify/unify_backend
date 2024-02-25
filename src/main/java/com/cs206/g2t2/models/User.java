package com.cs206.g2t2.models;

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

    // _id stores the user's autogenerated id
    @Id
    private String _id;

    // userCreationDate stores the date and time of the creation of the user
    private LocalDateTime userCreationDate;

    // username stores the user's username
    private String username;

    // email stores the user's email
    private String email;

    // password stores the encrypted password of the user
    private String password;

    // imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    //country stores the user's country of origin/base
    private String country;

    //language stores the user's preferred language
    private String language;

    //bsProfile stores the Brawl Stars Profile of the user
    private BsProfile bsProfile;

    //teams stores the list of teamId that the user is within
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

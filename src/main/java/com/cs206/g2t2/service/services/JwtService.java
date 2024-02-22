package com.cs206.g2t2.service.services;

import com.cs206.g2t2.models.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;


@Service
public interface JwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaim, User user);

    boolean isTokenValid(String token, UserDetails userDetails);

    Claims extractAllClaims(String token);

}


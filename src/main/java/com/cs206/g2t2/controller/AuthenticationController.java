package com.cs206.g2t2.controller;

import com.cs206.g2t2.data.request.auth.AuthenticationRequest;
import com.cs206.g2t2.data.request.auth.RegisterRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest request) {

        //If error found, exception will be thrown
        Response response = authenticationService.register(request);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@Valid @RequestBody AuthenticationRequest request) {

        //If error found, exception will be thrown
        Response response = authenticationService.authenticate(request);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Response> findUsername(@PathVariable String username) {

        //If error found, exception will be thrown
        Response response = authenticationService.findUsername(username);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);

    }
}
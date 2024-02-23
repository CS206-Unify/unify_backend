package com.cs206.g2t2.controller;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateBsProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<Response> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Find Profile based on the username by findProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.getUserProfile(username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<Response> findUserProfileByUsername(@PathVariable String username) {
        // Find a profile based on the username provided
        // Throws a InvalidUsername if username cannot be found in repository
        Response response = profileService.getUserProfile(username);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<Response> updateProfile(@Valid @RequestBody UpdateProfileRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = profileService.updateProfile(request, username);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/bsProfile")
    public ResponseEntity<Response> updateBsProfile(@Valid @RequestBody UpdateBsProfileRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = profileService.updateBsProfile(request, username);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Response> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = profileService.deleteUserProfile(username);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);
    }
}

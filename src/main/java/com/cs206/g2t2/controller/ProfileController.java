package com.cs206.g2t2.controller;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateBsPlayerTagRequest;
import com.cs206.g2t2.data.request.profile.UpdateGameProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.BrawlStarsAPIService;
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
    private final BrawlStarsAPIService brawlStarsAPIService;

    @GetMapping("/profile")
    public ResponseEntity<Response> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Find Profile based on the username by findProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.getUserProfileByUsername(username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/profile/username/{username}")
    public ResponseEntity<Response> findUserProfileByUsername(@PathVariable String username) {
        // Find a profile based on the username provided
        // Throws a InvalidUsername if username cannot be found in repository
        Response response = profileService.getUserProfileByUsername(username);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/profile/userId/{userId}")
    public ResponseEntity<Response> findUserProfileByUserId(@PathVariable String userId) {
        // Find a profile based on the username provided
        // Throws a InvalidUsername if username cannot be found in repository
        Response response = profileService.getUserProfileByUserId(userId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/bsStats")
    public ResponseEntity<Response> displayBsStats(@AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = brawlStarsAPIService.getBsStats(username);

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

    @PutMapping("/bsPlayerTag")
    public ResponseEntity<Response> updateBsPlayerTag(@Valid @RequestBody UpdateBsPlayerTagRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = profileService.updateBsPlayerTag(request, username);

        //Else, return ok response
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/bsStats")
    public ResponseEntity<Response> updateBsStats(@AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a UsernameNotFoundException if username cannot be found in repository
        Response response = brawlStarsAPIService.updateBsStats(username);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/bsProfile")
    public ResponseEntity<Response> updateBsProfile(@Valid @RequestBody UpdateGameProfileRequest request,
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


package com.cs206.g2t2.controller;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.DiscoverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discover")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DiscoverController {

    private final DiscoverService discoverService;

    @GetMapping("/team")
    public ResponseEntity<Response> discoverBsTeam(@RequestParam(required = false) String region,
                                                  @RequestParam(required = false) String language,
                                                  @RequestParam(required = false) Integer trophies,
                                                  @RequestParam(required = false) Integer threeVThreeWins,
                                                  @RequestParam(required = false) Integer twoVTwoWins,
                                                  @RequestParam(required = false) Integer soloWins,
                                                  @RequestParam Integer pageSize,
                                                  @RequestParam Integer pageNumber,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = discoverService.discoverBsTeam(username,region, language, trophies, threeVThreeWins,
                                                         twoVTwoWins, soloWins, pageSize, pageNumber);

        //If successful, the response is encapsulated with HTTP code of 201(created) and contains the BsTeam object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> discoverBsProfile(@RequestParam(required = false) String region,
                                                    @RequestParam(required = false) String language,
                                                    @RequestParam(required = false) Integer trophies,
                                                    @RequestParam(required = false) Integer threeVThreeWins,
                                                    @RequestParam(required = false) Integer twoVTwoWins,
                                                    @RequestParam(required = false) Integer soloWins,
                                                    @RequestParam Integer pageSize,
                                                    @RequestParam Integer pageNumber,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = discoverService.discoverBsProfile(username, region, language, trophies, threeVThreeWins,
                                                            twoVTwoWins, soloWins, pageSize, pageNumber);

        //If successful, the response is encapsulated with HTTP code of 201(created) and contains the BsTeam object
        return new ResponseEntity(response, HttpStatus.OK);
    }
}


package com.cs206.g2t2.controller;

import com.cs206.g2t2.data.request.team.TeamCreationRequest;
import com.cs206.g2t2.data.request.team.TeamUpdateRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.BsTeamService;
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
public class BsTeamController {

    private final BsTeamService bsTeamService;

    @PostMapping("/team")
    public ResponseEntity<Response> createTeam(@Valid @RequestBody TeamCreationRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.createBsTeam(request, username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/team")
    public ResponseEntity<Response> updateTeam(@Valid @RequestBody TeamUpdateRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.updateBsTeam(request, username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @PutMapping("/team")
//    public ResponseEntity<Response> addMember(@RequestParam(name = "teamName") String teamName,
//                                              @RequestParam(name = "addUsername") String addUsername,
//                                              @AuthenticationPrincipal UserDetails userDetails) {
//
//        // Get the username from the userDetails of the authenticated user
//        String username = userDetails.getUsername();
//
//        //Create team using createTeam method in bsTeamService
//        Response response = bsTeamService.addMember(username, teamName, addUsername);
//
//        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
}


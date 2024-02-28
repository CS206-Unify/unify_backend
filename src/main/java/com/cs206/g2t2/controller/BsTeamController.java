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

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Response> getBsTeamByTeamId(@PathVariable("teamId") String teamId) {
        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.getBsTeamByTeamId(teamId);

        //If successful, the response is encapsulated with HTTP code of 201(created) and contains the BsTeam object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/team")
    public ResponseEntity<Response> createBsTeam(@Valid @RequestBody TeamCreationRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.createBsTeam(request, username);

        //If successful, the response is encapsulated with HTTP code of 201(created) and contains the BsTeam object
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("/team/{teamId}")
    public ResponseEntity<Response> updateBsTeam(@Valid @RequestBody TeamUpdateRequest request,
                                                 @PathVariable("teamId") String teamId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.updateBsTeam(request, teamId, username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the updated BsTeam object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/team/{teamId}/member/{memberId}")
    public ResponseEntity<Response> addMember(@PathVariable("teamId") String teamId,
                                              @PathVariable("memberId") String memberId,
                                              @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.addMember(username, teamId, memberId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/team/{teamId}/member/{memberId}/promote")
    public ResponseEntity<Response> promoteMember(@PathVariable("teamId") String teamId,
                                                  @PathVariable("memberId") String memberId,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.promoteMember(username, teamId, memberId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/team/{teamId}/member/{memberId}/demote")
    public ResponseEntity<Response> demoteMember(@PathVariable("teamId") String teamId,
                                                 @PathVariable("memberId") String memberId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Create team using createTeam method in bsTeamService
        Response response = bsTeamService.demoteMember(username, teamId, memberId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return new ResponseEntity(response, HttpStatus.OK);
    }
}


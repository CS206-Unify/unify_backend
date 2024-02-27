package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.team.TeamCreationRequest;
import com.cs206.g2t2.data.request.team.TeamUpdateRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.exceptions.badRequest.TeamIsFullException;
import com.cs206.g2t2.exceptions.notFound.TeamNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface BsTeamService {


    Response createBsTeam(TeamCreationRequest request, String username);

    Response updateBsTeam(TeamUpdateRequest request, String username);

    Response addMember(String username, String teamName, String addUserName)
            throws UserNotFoundException, TeamNotFoundException, TeamIsFullException;
}

package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.team.TeamCreationRequest;
import com.cs206.g2t2.data.request.team.TeamUpdateRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.exceptions.badRequest.TeamIsFullException;
import com.cs206.g2t2.exceptions.notFound.MemberNotFoundException;
import com.cs206.g2t2.exceptions.notFound.TeamNameNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UserNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface BsTeamService {

    Response getBsTeamByTeamId(String teamId);

    Response createBsTeam(TeamCreationRequest request, String username);

    Response updateBsTeam(TeamUpdateRequest request, String teamId, String username);

    Response addMember(String username, String teamName, String addUserName)
            throws UsernameNotFoundException, UserNotFoundException, TeamNameNotFoundException, TeamIsFullException;

    Response promoteMember(String username, String teamName, String addUserName)
            throws UsernameNotFoundException, UserNotFoundException, TeamNameNotFoundException, MemberNotFoundException;
}

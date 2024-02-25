package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.team.TeamCreationRequest;
import com.cs206.g2t2.data.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface BsTeamService {


    Response createBsTeam(TeamCreationRequest request, String username);
}

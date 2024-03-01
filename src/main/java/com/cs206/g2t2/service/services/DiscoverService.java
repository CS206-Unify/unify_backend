package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface DiscoverService {

    Response discoverTeam(String username, String region, String language, Integer trophies,
                          Integer threeVThreeWins, Integer twoVTwoWins, Integer soloWins,
                          Integer pageSize, Integer pageNumber);

    Response discoverProfile(String username, String region, String language, Integer trophies,
                             Integer threeVThreeWins, Integer twoVTwoWins, Integer soloWins,
                             Integer pageSize, Integer pageNumber);

}
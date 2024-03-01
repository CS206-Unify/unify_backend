package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.brawlStars.BsPlayerBattleLogResponse;
import org.springframework.stereotype.Service;

@Service
public interface BrawlStarsAPIService {

    /**
     * Finds a user's Brawl Star player Stats from external API.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String containing the username of the user obtained from the token
     * @return BsPlayerResponse containing the Player object in the body
     */
    Response getBsStats(String username);

    /**
     * Updates a user's Brawl Star player Stats from external API into the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Stats has been updated successfully"
     */
    Response updateBsStats(String username);

    /**
     * Finds a user's Brawl Star player battle log from external API.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String containing the username of the user obtained from the token
     * @return BsPlayerBattleLogResponse containing the BattleLog object in the body
     */
    Response getBsBattleLog(String username);

    /**
     * Updates a user's Brawl Star player battle log from external API into the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Battle Log has been updated successfully"
     */
    Response updateBsBattleLog(String username);
}

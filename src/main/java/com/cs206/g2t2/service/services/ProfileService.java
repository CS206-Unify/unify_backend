package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateBsPlayerTagRequest;
import com.cs206.g2t2.data.request.profile.UpdateGameProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.bsTeam.MultiBsTeamResponse;
import com.cs206.g2t2.data.response.user.SingleUserResponse;
import com.cs206.g2t2.exceptions.notFound.TeamNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UserNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {

    /**
     * Finds a user with the inputted username
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String object containing the username to be searched
     * @return SingleUserResponse object containing the user
     */
    Response getUserProfileByUsername(String username) throws UsernameNotFoundException;

    /**
     * Finds a user with the inputted userId
     * If userId cannot be found in the repository, throw a UserNotFoundException.
     *
     * @param userId a String object containing the userId to be searched
     * @return SingleUserResponse object containing the user
     */
    Response getUserProfileByUserId(String userId) throws UserNotFoundException;

    /**
     * Finds all teams that the user is in
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     * If teamId cannot be found in the repository, throw a TeamNotFoundException.
     *
     * @param username a String object containing the username to be searched
     * @return MultiBsTeamResponse object containing the user's teams
     */
    Response getUserBsTeams(String username) throws UsernameNotFoundException, TeamNotFoundException;

    /**
     * Updates a user's profile in the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param request a UpdateProfileRequest object containing the new user profile info to be updated
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Profile has been updated successfully"
     */
    Response updateProfile(UpdateProfileRequest request, String username) throws UsernameNotFoundException;

    /**
     * Updates a user's Brawl Star Player Tag in the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param request a UpdateBsPlayerTagRequest containing the new user's Brawl Star ID to be updated in database
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Player Tag has been updated successfully"
     */
    Response updateBsPlayerTag(UpdateBsPlayerTagRequest request, String username) throws UsernameNotFoundException;

    /**
     * Updates a user's Brawl Star profile in the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param request a UpdateGameProfileRequest object containing the new user's Brawl Star profile info to be updated
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star Profile has been updated successfully"
     */
    Response updateBsProfile(UpdateGameProfileRequest request, String username) throws UsernameNotFoundException;

    /**
     * Deletes a user from the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param username a String object containing the username of user to be deleted
     * @return SuccessResponse "User has been deleted successfully"
     */
    Response deleteUserProfile(String username) throws UsernameNotFoundException;
}

package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateBsPlayerTagRequest;
import com.cs206.g2t2.data.request.profile.UpdateGameProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
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
    Response getUserProfile(String username) throws UsernameNotFoundException;

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
     * Updates a user's Brawl Star ID in the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param request a UpdateBsIdRequest containing the new user's Brawl Star ID to be updated in database
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star ID has been updated successfully"
     */
    Response updateBsPlayerTag(UpdateBsPlayerTagRequest request, String username) throws UsernameNotFoundException;

    /**
     * Updates a user's Brawl Star profile in the repository.
     * If username cannot be found in the repository, throw a UsernameNotFoundException.
     *
     * @param request a UpdateProfileRequest object containing the new user's Brawl Star profile info to be updated
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

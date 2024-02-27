package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.auth.AuthenticationRequest;
import com.cs206.g2t2.data.request.auth.RegisterRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.exceptions.badRequest.DuplicatedUsernameException;
import com.cs206.g2t2.exceptions.unauthorized.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     * Adds a new user to the repository.
     * If username can be found in the repository, throw a DuplicatedUsernameException.
     *
     * @param request a RegisterRequest object containing the new user info to be created
     * @return AuthenticationResponse with information on the jwt token to be returned to the user for
     * authenticated api path access
     */
    Response register(RegisterRequest request);

    /**
     * Authenticate a user.
     * If authentication fails, throws InvalidCredentialsException.
     *
     * @param request a AuthenticationRequest object containing the username and password of user to be authenticated
     * @return SuccessResponse "User has been created successfully"
     */
    Response authenticate(AuthenticationRequest request) throws InvalidCredentialsException;

    /**
     * Finds if user with inputted username exists in repository.
     * If such a user exists, throws DuplicatedUsernameException.
     *
     * @param username a String object containing the username to be checked
     * @return SuccessResponse "Username is available"
     */
    Response findUsername(String username) throws DuplicatedUsernameException;
}

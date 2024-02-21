package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.request.AuthenticationRequest;
import com.cs206.g2t2.data.request.RegisterRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.exceptions.DuplicatedUsernameException;
import com.cs206.g2t2.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     * Adds a new user to the repository.
     * If username can be found in the repository, throw a DuplicatedUsernameException.
     * For updating, if all three password fields are not the same or wrong oldPassword is inputted, throw a
     * PasswordDoNotMatchException
     *
     * @param request a RegisterRequest object containing the new user info to be created
     * @return SuccessResponse "User has been created successfully"
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

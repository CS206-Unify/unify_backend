package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.request.auth.AuthenticationRequest;
import com.cs206.g2t2.data.request.auth.RegisterRequest;
import com.cs206.g2t2.data.response.AuthenticationResponse;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.exceptions.badRequest.DuplicatedEmailException;
import com.cs206.g2t2.exceptions.badRequest.DuplicatedUsernameException;
import com.cs206.g2t2.exceptions.unauthorized.InvalidCredentialsException;
import com.cs206.g2t2.exceptions.notFound.UserNotFoundException;
import com.cs206.g2t2.models.BsProfile;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.AuthenticationService;
import com.cs206.g2t2.service.services.CommonService;
import com.cs206.g2t2.service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      //From ApplicationConfig.java
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CommonService commonService;

    /**
     * Private helper: Searches for users in the repository which has the same username.
     * If user with username can be found in the repository, throw a DuplicatedUsernameException.
     *
     * @param username String object containing the username
     */
    private void hasExistingUserWithUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicatedUsernameException(username);
        }
    }

    /**
     * Private helper: Searches for users in the repository which has the same email.
     * If user with email can be found in the repository, throw a DuplicatedEmailException.
     *
     * @param email String object containing the email
     */
    private void hasExistingUserWithEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicatedEmailException(email);
        }
    }

    /**
     * @param request RegisterRequest object containing the new user info to be created
     * @return AuthenticationResponse with information on the jwt token to be returned to the user for
     * authenticated api path access
     */
    public Response register(RegisterRequest request) {

        //If username is present, throw new DuplicatedUsernameException
        hasExistingUserWithUsername(request.getUsername());

        //If email is present, throw new DuplicatedEmailException
        hasExistingUserWithEmail(request.getEmail());

        //Create User Object
        User user = User.builder()
                .userCreationDate(LocalDateTime.now())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .imageString(null)
                .language("Any")
                .country("Any")
                .bsProfile(new BsProfile())
                .teams(new ArrayList<String>())
                .build();

        //Save user into the repository
        userRepository.save(user);

        //Finds User object from database by username
        User retrievedUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername()));

        //If authenticated, create jwt token and return an AuthenticationResponse containing jwt token
        String jwtToken = jwtService.generateToken(retrievedUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * @param request AuthenticationRequest object containing the username and password of user to be authenticated
     * @return AuthenticationResponse with information on the jwt token to be returned to the user for
     *      authenticated api path access
     */
    public Response authenticate(AuthenticationRequest request) {

        //Attempt to perform authentication
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        //If Authentication fails, the BadCredentialsException will be caught.
        } catch (BadCredentialsException e) {
            //Throws an InvalidCredentialsException, if username or password is incorrect.
            throw new InvalidCredentialsException();
        }

        //Finds User object from database by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername()));

        //If authenticated, create jwt token and return an AuthenticationResponse containing jwt token
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     *
     * @param username a String object containing the username to be checked
     * @return a SuccessResponse "Username is available" if the username has not yet been used
     *      and therefore can be utilised for creating a new user
     *      otherwise throw DuplicatedUsernameException to show it has been used
     */
    public Response findUsername(String username) {

        //If username exists, throw new DuplicatedUsernameException
        hasExistingUserWithUsername(username);

        //If Everything goes smoothly, response will be created using SuccessResponse
        return SuccessResponse.builder()
                .message("Username is available")
                .build();
    }
}

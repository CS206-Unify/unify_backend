package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.request.AuthenticationRequest;
import com.cs206.g2t2.data.request.RegisterRequest;
import com.cs206.g2t2.data.response.AuthenticationResponse;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.exceptions.badRequest.DuplicatedUsernameException;
import com.cs206.g2t2.exceptions.unauthorized.InvalidCredentialsException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.BSProfile;
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
     * @param request RegisterRequest object containing the new user info to be created
     * @return SuccessResponse containing information "User has been created successfully"
     *      or throws the relevant exception from the getUserClassFromRequest method in commonService
     */
    public Response register(RegisterRequest request) {

        //Create User Object
        User user = User.builder()
                .userCreationDate(LocalDateTime.now())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .imageString(null)
                .languages(new ArrayList<String>())
                .bsProfile(new BSProfile())
                .teams(new ArrayList<String>())
                .build();

        //Save user into the repository
        userRepository.save(user);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("User has been created successfully")
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
                .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

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
        //If username exists, throw new DuplicatedUsernameException(username)
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicatedUsernameException(username);
        }

        //If Everything goes smoothly, response will be created using SuccessResponse
        return SuccessResponse.builder()
                .response("Username is available")
                .build();
    }
}

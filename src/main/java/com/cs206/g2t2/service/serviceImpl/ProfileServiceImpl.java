package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateBsProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.data.response.user.SingleUserResponse;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    /**
     * @param username a String object containing the username to be searched
     * @return SingleUserResponse object containing the user
     */
    @Override
    public Response getUserProfile(String username) throws UsernameNotFoundException {
        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Returns SingleUserResponse if user can be found
        return SingleUserResponse.builder()
                .user(user)
                .build();
    }

    /**
     * @param request a UpdateProfileRequest object containing the new user profile info to be updated
     * @return SuccessResponse "User Profile has been updated successfully"
     */
    @Override
    public Response updateProfile(UpdateProfileRequest request, String username) throws UsernameNotFoundException {

        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Updates country and language into user
        user.setCountry(request.getCountry());
        user.setLanguage(request.getLanguage());

        //Updates imageString into user if request.imageString is not null
        if (request.getImageString() != null) { user.setImageString(request.getImageString()); }

        //Saves user back into repository
        userRepository.save(user);

        //Returns SuccessResponse if all information could be updated
        return SuccessResponse.builder()
                .message("User's Profile has been updated successfully")
                .build();
    }

    /**
     * @param request a UpdateProfileRequest object containing the new user Brawl Star profile info to be updated
     * @return SuccessResponse "User Brawl Star Profile has been updated successfully"
     */
    @Override
    public Response updateBsProfile(UpdateBsProfileRequest request, String username) throws UsernameNotFoundException {

        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Updates the region and personal bio
        user.getBsProfile().setRegion(request.getRegion());
        user.getBsProfile().setPersonalBio(request.getPersonalBio());

        //Saves user back into repository
        userRepository.save(user);

        //Returns SuccessResponse if all information could be updated
        return SuccessResponse.builder()
                .message("User's Brawl Stars Profile has been updated successfully")
                .build();
    }

    /**
     * @param username a String object containing the username of user to be deleted
     * @return SuccessResponse "User has been deleted successfully"
     */
    @Override
    public Response deleteUserProfile(String username) throws UsernameNotFoundException {
        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Deletes user from repository
        userRepository.deleteByUsername(username);

        //Returns SingleUserResponse if user can be found
        return SuccessResponse.builder()
                .message("User has been deleted successfully")
                .build();
    }
}

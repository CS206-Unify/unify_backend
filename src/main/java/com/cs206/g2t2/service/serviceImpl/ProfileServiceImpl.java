package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.request.auth.UpdateProfileRequest;
import com.cs206.g2t2.data.request.profile.UpdateGameProfileRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.bsTeam.MultiBsTeamListingResponse;
import com.cs206.g2t2.data.response.bsTeam.MultiBsTeamResponse;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.data.response.user.SingleUserResponse;
import com.cs206.g2t2.exceptions.badRequest.InvalidBrawlStarsPlayerTagException;
import com.cs206.g2t2.exceptions.notFound.MemberNotFoundException;
import com.cs206.g2t2.exceptions.notFound.TeamNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UserNotFoundException;
import com.cs206.g2t2.exceptions.notFound.UsernameNotFoundException;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.models.team.BsTeam;
import com.cs206.g2t2.models.team.BsTeamListing;
import com.cs206.g2t2.models.team.TeamMember;
import com.cs206.g2t2.repository.BsTeamRepository;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final BsTeamRepository bsTeamRepository;

    /**
     * @param username a String object containing the username to be searched
     * @return SingleUserResponse object containing the user
     */
    @Override
    public Response getUserProfileByUsername(String username) throws UsernameNotFoundException {
        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Returns SingleUserResponse if user can be found
        return SingleUserResponse.builder()
                .user(user)
                .build();
    }

    /**
     * @param userId a String object containing the userId to be searched
     * @return SingleUserResponse object containing the user
     */
    @Override
    public Response getUserProfileByUserId(String userId) throws UserNotFoundException {
        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findBy_id(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        //Returns SingleUserResponse if user can be found
        return SingleUserResponse.builder()
                .user(user)
                .build();
    }

    /**
     * @param username a String object containing the username to be searched
     * @return MultiBsTeamResponse object containing the user's teams
     */
    @Override
    public Response getUserBsTeams(String username) throws UserNotFoundException {
        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Obtain all teamId under the user
        List<String> bsTeamIdList = user.getTeams();

        //Store all found teams into a List
        List<BsTeamListing> bsTeamListings = new ArrayList<BsTeamListing>();
        for (String teamId : bsTeamIdList) {
            BsTeam bsTeam = bsTeamRepository.findBy_id(teamId)
                    .orElseThrow(() -> new TeamNotFoundException(teamId));
            bsTeamListings.add(new BsTeamListing(bsTeam));
        }

        //Returns SingleUserResponse if user can be found
        return MultiBsTeamListingResponse.builder()
                .bsTeamListings(bsTeamListings)
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
    public Response updateBsProfile(UpdateGameProfileRequest request, String username) throws UsernameNotFoundException {

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
     * @param playerTag a String containing the new user's playerTag to be updated in database
     * @param username a String containing the username of the user obtained from the token
     * @return SuccessResponse "User's Brawl Star ID has been updated successfully"
     */
    public Response updateBsPlayerTag(String playerTag, String username) throws UsernameNotFoundException {

        //Finds user in repository else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Perform check if player tag is valid
        if (playerTag != null && playerTag.charAt(0) == '#') {
            throw new InvalidBrawlStarsPlayerTagException(playerTag);
        }

        //Updates the region and personal bio
        user.getBsProfile().setPlayerTag(playerTag);

        //Saves user back into repository
        userRepository.save(user);

        //Returns SuccessResponse if all information could be updated
        return SuccessResponse.builder()
                .message("User's Brawl Star ID has been updated successfully")
                .build();
    }

    /**
     * Private Helper: Find team member in the bsTeam
     * If team member not found, throws MemberNotFoundException
     *
     * @param user a User object containing the user to be found
     * @param bsTeam a BSTeam object that stores the bsTeam to find user
     * @return TeamMember object containing the user's id
     */
    private TeamMember findTeamMember(User user, BsTeam bsTeam) throws MemberNotFoundException {
        //Searches through the memberList for the teamMember
        for (TeamMember teamMember : bsTeam.getMemberList()) {
            //If TeamMember object id tallies with userId
            if (teamMember.getUserId().equals(user.get_id())) {
                //Returns TeamMember object if found
                return teamMember;
            }
        }
        //Throws MemberNotFoundException if user cannot be found in the memberList
        throw new MemberNotFoundException(user.getUsername(), bsTeam.getTeamName());
    }

    /**
     * Private Helper: Transfers BsTeam ownership from user to the next best successor based on role, then join date
     * If successor not found, deletes the bsTeam from repository
     *
     * @param user a User object containing the user holding ownership
     * @param userMember a TeamMember object of the user to be deleted
     * @param bsTeam a BSTeam object that stores the bsTeam to perform transfer of ownership
     */
    private void transferBsTeamOwnership(User user, TeamMember userMember, BsTeam bsTeam) {
        //Stores the team member to be promoted to owner
        TeamMember successor = null;
        //Stores the role of the team member to be promoted to owner
        TeamMember.Role successorRole = TeamMember.Role.MEMBER;
        //Stores the joinDate of the successor
        LocalDateTime joinDate = LocalDateTime.now();

        //Searches through the memberList for a successor by finding the highest role, followed by longest serving
        for (TeamMember teamMember : bsTeam.getMemberList()) {
            //Only looks at teamMember which are not the user
            if (!teamMember.getUserId().equals(user.get_id())) {
                //If teamMember's role is higher than the current successor's role
                //Or teamMember's role is equals to the current successor's role and joinDate is earlier
                if (teamMember.getRole().isHigherRole(successorRole) ||
                   (teamMember.getRole().isEqualRole(successorRole) && teamMember.getJoinDate().isBefore(joinDate))) {
                    //Change current successor to the team member
                    successor = teamMember;
                    successorRole = teamMember.getRole();
                    joinDate = teamMember.getJoinDate();
                }
            }
        }

        //If no such member can be found, delete team
        if (successor == null) {
            bsTeamRepository.deleteById(bsTeam.get_id());
            return;
        }

        //Perform transfer ownership
        successor.setRole(TeamMember.Role.OWNER);

        //Delete user from memberList
        bsTeam.getMemberList().remove(userMember);

        //Save bsTeam into bsTeamRepository
        bsTeamRepository.save(bsTeam);
    }

    /**
     * @param username a String object containing the username of user to be deleted
     * @return SuccessResponse "User has been deleted successfully"
     */
    @Override
    public Response deleteUserProfile(String username) throws UsernameNotFoundException {
        //Finds user in repository, else throws UsernameNotFoundException
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Find all teams that user is in
        List<String> bsTeamList = user.getTeams();
        for (String bsTeamId : bsTeamList) {
            //Obtains team from bsTeamRepository
            BsTeam bsTeam = bsTeamRepository.findBy_id(bsTeamId)
                    .orElseThrow(() -> new TeamNotFoundException(bsTeamId));
            //Finds TeamMember from the bsTeam's member list
            TeamMember userMember = findTeamMember(user, bsTeam);
            //Performs transfer ownership if user is an owner and delete bsTeam if no successor found
            if (userMember.getRole() == TeamMember.Role.OWNER) {
                transferBsTeamOwnership(user, userMember, bsTeam);
            }
        }

        //Deletes user from repository
        userRepository.deleteByUsername(username);

        //Returns SingleUserResponse if user can be found
        return SuccessResponse.builder()
                .message("User has been deleted successfully")
                .build();
    }
}

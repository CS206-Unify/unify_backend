package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.request.team.TeamCreationRequest;
import com.cs206.g2t2.data.request.team.TeamUpdateRequest;
import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.data.response.bsTeam.SingleBsTeamResponse;
import com.cs206.g2t2.data.response.common.SuccessResponse;
import com.cs206.g2t2.exceptions.badRequest.DuplicatedTeamNameException;
import com.cs206.g2t2.exceptions.badRequest.RoleChangeNotAllowedException;
import com.cs206.g2t2.exceptions.badRequest.TeamIsFullException;
import com.cs206.g2t2.exceptions.forbidden.ForbiddenException;
import com.cs206.g2t2.exceptions.notFound.*;
import com.cs206.g2t2.models.BsTeam;
import com.cs206.g2t2.models.TeamMember;
import com.cs206.g2t2.models.User;
import com.cs206.g2t2.repository.BsTeamRepository;
import com.cs206.g2t2.repository.UserRepository;
import com.cs206.g2t2.service.services.BsTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BsTeamServiceImpl implements BsTeamService {

    private final UserRepository userRepository;
    private final BsTeamRepository bsTeamRepository;

    private BsTeam saveAndReturnBsTeamInRepository(TeamCreationRequest request)
            throws DuplicatedTeamNameException, TeamNameNotFoundException {

        //Stores username in a variable
        String teamName = request.getTeamName();

        //Checks if Team's team name already exists in database
        if (bsTeamRepository.findByTeamName(teamName).isPresent()) {
            throw new DuplicatedTeamNameException(teamName);
        }

        //Creates bsTeam from TeamCreationRequest
        BsTeam bsTeam = BsTeam.builder()
                .teamName(request.getTeamName())
                .region(request.getRegion())
                .maximumTeamSize(request.getMaximumTeamSize())
                .imageString(null)
                .trophyRequirements(0)
                .min3v3Wins(0)
                .minDuoWins(0)
                .minSoloWins(0)
                .memberList(new ArrayList<TeamMember>())
                .build();

        //Saves bsTeam into repository
        bsTeamRepository.save(bsTeam);

        //Returns bsTeam stored in repository to caller
        return bsTeamRepository.findByTeamName(teamName).orElseThrow(() -> new TeamNameNotFoundException(teamName));
    }

    private void isOwnerOrAdminUser(User user, BsTeam bsTeam) throws ForbiddenException {
        //Finds current user from the bsTeam and check if the user is an Admin/Owner
        boolean found = false;
        for (TeamMember teamMember : bsTeam.getMemberList()) {
            if (teamMember.getUserId().equals(user.get_id()) &&
               (teamMember.getRole() == TeamMember.Role.ADMIN || teamMember.getRole() == TeamMember.Role.OWNER)) {
                found = true;
            }
        }

        //If the user is not an admin member of the team, throw new ForbiddenException
        if (!found) { throw new ForbiddenException(); }
    }

    @Override
    public Response getBsTeamByTeamId(String teamId) {

        //Finds BsTeam in bsTeamRepository by teamId
        BsTeam bsTeam = bsTeamRepository.findBy_id(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        //Return SingleBsTeamResponse
        return SingleBsTeamResponse.builder()
                .bsTeam(bsTeam)
                .build();
    }

    @Override
    public Response createBsTeam(TeamCreationRequest request, String username) throws UsernameNotFoundException {

        //Find user from userRepository
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        TeamMember teamMember = TeamMember.builder()
                .userId(user.get_id())
                .role(TeamMember.Role.OWNER)
                .joinDate(LocalDateTime.now())
                .build();

        //Obtains bsTeam from repository (Contains autogenerated _id)
        BsTeam bsTeam = saveAndReturnBsTeamInRepository(request);

        //Save the teamId into user's list of teamsId
        user.getTeams().add(bsTeam.get_id());

        //Save the teamMember into bsTeam
        bsTeam.getMemberList().add(teamMember);

        //Save both bsTeam and user into respective repository
        userRepository.save(user);
        bsTeamRepository.save(bsTeam);

        //Return SingleBsTeamResponse
        return SingleBsTeamResponse.builder()
                .bsTeam(bsTeam)
                .build();
    }

    @Override
    public Response updateBsTeam(TeamUpdateRequest request, String teamId, String username)
            throws UsernameNotFoundException, TeamNameNotFoundException, ForbiddenException {

        //Find Team from userRepository
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        //Find Team from bsTeamRepository
        BsTeam bsTeam = bsTeamRepository.findBy_id(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        //Checks if current user is an admin user or owner, else throws ForbiddenException
        isOwnerOrAdminUser(user, bsTeam);

        //Perform update of all fields
        bsTeam.setTrophyRequirements(request.getTrophyRequirements());
        bsTeam.setMin3v3Wins(request.getMin3v3Wins());
        bsTeam.setMinDuoWins(request.getMinDuoWins());
        bsTeam.setMinSoloWins(request.getMinSoloWins());
        if (request.getImageString() != null) {
            bsTeam.setImageString(request.getImageString());
        }

        //Save bsTeam into teamRepository
        bsTeamRepository.save(bsTeam);

        //Return SingleBsTeamResponse
        return SingleBsTeamResponse.builder()
                .bsTeam(bsTeam)
                .build();
    }

    @Override
    public Response addMember(String username, String teamId, String memberId)
            throws UsernameNotFoundException, UserNotFoundException, TeamNameNotFoundException, TeamIsFullException {

        //Find User from userRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Find Member from userRepository
        User member = userRepository.findBy_id(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        //Find Team from bsTeamRepository
        BsTeam bsTeam = bsTeamRepository.findBy_id(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        //Checks if current user is an admin user or owner, else throws ForbiddenException
        isOwnerOrAdminUser(user, bsTeam);

        //Checks if current team is full already, if so throw TeamIsFullException
        if (bsTeam.getMemberList().size() == bsTeam.getMaximumTeamSize()) {
            throw new TeamIsFullException(bsTeam.getTeamName(), bsTeam.getMaximumTeamSize());
        }

        //Create TeamMember Object and add to the bsTeam as a member
        TeamMember teamMember = TeamMember.builder()
                .userId(member.get_id())
                .role(TeamMember.Role.MEMBER)
                .joinDate(LocalDateTime.now())
                .build();
        bsTeam.getMemberList().add(teamMember);

        //Add Team to members TeamList
        member.getTeams().add(teamId);

        //Save member and bsTeam into userRepository and teamRepository respectively
        bsTeamRepository.save(bsTeam);
        userRepository.save(member);

        //Return SuccessResponse
        return SuccessResponse.builder()
                .message("Team Member " + member.getUsername() + " has been added successfully into " + bsTeam.getTeamName())
                .build();
    }

    private void performPromotionOrDemotion(User user, User member, BsTeam bsTeam, boolean promotion)
            throws MemberNotFoundException, RoleChangeNotAllowedException{
        //Find both Team Members of the bsTeam
        TeamMember foundUser = null;
        TeamMember foundMember = null;
        for (TeamMember teamMember : bsTeam.getMemberList()) {
            if (teamMember.getUserId().equals(user.get_id())) {
                foundUser = teamMember;
            } else if (teamMember.getUserId().equals(member.get_id())) {
                foundMember = teamMember;
            }
        }

        //Checks if both found user and found member has been found of not throw MemberNotFoundException
        if (foundUser == null || foundMember == null) {
            throw new MemberNotFoundException((foundUser == null ? user.get_id() : member.getUsername()), bsTeam.getTeamName());
        }

        //Obtain user role and member role for easier access
        TeamMember.Role userRole = foundUser.getRole();
        TeamMember.Role memberRole = foundMember.getRole();

        // If action required is promotion and member has role MEMBER
        if (promotion && memberRole == TeamMember.Role.MEMBER) {
            //Action is only allowed is user has Role ADMIN or Role OWNER
            if (userRole == TeamMember.Role.OWNER || userRole == TeamMember.Role.ADMIN ) {
                //Promote member to role ADMIN
                foundMember.setRole(TeamMember.Role.ADMIN);
            } else {
                throw new ForbiddenException();
            }

        // If action required is promotion and member has role MEMBER
        } else if (promotion && memberRole == TeamMember.Role.ADMIN){
            //Action is only allowed is user has Role OWNER
            if (userRole == TeamMember.Role.OWNER) {
                //Transfer Ownership from user to member
                foundUser.setRole(TeamMember.Role.ADMIN);
                foundMember.setRole(TeamMember.Role.OWNER);
            } else {
                throw new ForbiddenException();
            }

        // If action required is demotion and member has role ADMIN
        } else if (!promotion && memberRole == TeamMember.Role.ADMIN) {
            //Action is only allowed is user has Role OWNER
            if (userRole == TeamMember.Role.OWNER) {
                //Demote member to role MEMBER
                foundMember.setRole(TeamMember.Role.MEMBER);
            } else {
                throw new ForbiddenException();
            }

        // If requirements not met, throw RoleChangeNotAllowedException
        } else {
            throw new RoleChangeNotAllowedException(
                    promotion ? RoleChangeNotAllowedException.PROMOTION : RoleChangeNotAllowedException.DEMOTION,
                    userRole.toString(),
                    memberRole.toString());
        }

        //Save to repository
        bsTeamRepository.save(bsTeam);
    }

    @Override
    public Response promoteMember(String username, String teamId, String memberId)
            throws UsernameNotFoundException, UserNotFoundException, TeamNameNotFoundException {

        //Find User from userRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Find Member from userRepository
        User member = userRepository.findBy_id(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        //Find Team from bsTeamRepository
        BsTeam bsTeam = bsTeamRepository.findBy_id(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        //Perform Promotion
        performPromotionOrDemotion(user, member, bsTeam, true);

        //Return SuccessResponse
        return SuccessResponse.builder()
                .message("Team Member " + member.getUsername() + " has been promoted successfully")
                .build();
    }

    @Override
    public Response demoteMember(String username, String teamId, String memberId)
            throws UsernameNotFoundException, UserNotFoundException, TeamNameNotFoundException {

        //Find User from userRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //Find Member from userRepository
        User member = userRepository.findBy_id(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        //Find Team from bsTeamRepository
        BsTeam bsTeam = bsTeamRepository.findBy_id(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        //Perform Promotion
        performPromotionOrDemotion(user, member, bsTeam, false);

        //Return SuccessResponse
        return SuccessResponse.builder()
                .message("Team Member " + member.getUsername() + " has been demoted successfully")
                .build();
    }
}

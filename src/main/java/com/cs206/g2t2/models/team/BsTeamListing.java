package com.cs206.g2t2.models.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BsTeamListing {

    // _id stores the teamID
    private String _id;

    // teamName stores the team's name
    private String teamName;

    // currentTeamSize stores the current size of the team
    private int currentTeamSize;

    // maximumTeamSize stores the maximum number of members of the team
    private int maximumTeamSize;

    // imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    // trophyRequirements stores the number of trophies that user need before joining the team
    private int trophyRequirements;

    // min3v3Wins stores the number of wins in 3v3 game mode that user needs before joining the team
    private int min3v3Wins;

    // minDuoWins stores the number of wins in 2v2 game mode that user needs before joining the team
    private int minDuoWins;

    // minSoloWins stores the number of wins in 1v1 game mode that user needs before joining the team
    private int minSoloWins;

    //Constructor to allow creation of BsTeamListing from BsTeam
    public BsTeamListing(BsTeam bsTeam) {
        this._id = bsTeam.get_id();
        this.teamName = bsTeam.getTeamName();
        this.currentTeamSize = bsTeam.getMemberList().size();
        this.maximumTeamSize = bsTeam.getMaximumTeamSize();
        this.imageString = bsTeam.getImageString();
        this.trophyRequirements = bsTeam.getTrophyRequirements();
        this.min3v3Wins = bsTeam.getMin3v3Wins();
        this.minDuoWins = bsTeam.getMinDuoWins();
        this.minSoloWins = bsTeam.getMinSoloWins();
    }
}

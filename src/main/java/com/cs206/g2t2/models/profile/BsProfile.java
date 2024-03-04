package com.cs206.g2t2.models.profile;

import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BsProfile {

    //Contains the user's brawl star ID
    private String playerTag;

    //Contains the user's stats
    private Player player;

    //Contains the user's region of BS play
    private String region;

    //Contains the user's personal bio of BS
    private String personalBio;

}

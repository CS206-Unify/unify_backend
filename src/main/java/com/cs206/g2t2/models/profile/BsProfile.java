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

    //bestBrawlerId stores the user's best brawler id
    private long bestBrawlerId;

    //bestBrawlerName stores the user's best brawler name
    private String bestBrawlerName;

    //rank stores the rank of the best brawler
    private int bestBrawlerRank;

    //wins stores the number of wins in the past 25 battles
    private int wins;

    //losses stores the number of losses in the past 25 battles
    private int losses;

    //draws stores the number of draws in the past 25 battles
    private int draws;

}

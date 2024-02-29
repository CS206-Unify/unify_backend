package com.cs206.g2t2.models;

import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Brawler;
import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BsProfileListing { // Implements UserDetails so that the security.core library can be used

    //_id stores the userId of the user
    private String _id;

    // username stores the user's username
    private String username;

    // imageString stores the S3 image link, can be null if there is no image
    private String imageString;

    //country stores the user's country of origin/base
    private String country;

    //region stores the user's region of BS play
    private String region;

    //trophies stores the number of trophies of the user
    private int trophies;

    //threeVThreeWins stores the number of 3vs3 wins
    private int threeVThreeWins;

    //bestBrawlerId stores the user's best brawler id
    private long bestBrawlerId;

    //bestBrawlerName stores the user's best brawler name
    private String bestBrawlerName;

    //rank stores the rank of the best brawler
    private int bestBrawlerRank;

    private void findAndUpdateBestBrawler(Player player) {
        //Search for best brawler by sorting the list
        List<Brawler> brawlerList = new ArrayList<>(player.getBrawlers());
        Collections.sort(brawlerList);
        Brawler bestBrawler = brawlerList.get(brawlerList.size() - 1);
        this.bestBrawlerId = bestBrawler.getId();
        this.bestBrawlerName = bestBrawler.getName();
        this.bestBrawlerRank = bestBrawler.getRank();
    }

    //Constructor
    public BsProfileListing(User user) {
        //Update standard fields from user
        this._id = user.get_id();
        this.username = user.getUsername();
        this.imageString = user.getImageString();
        this.country = user.getCountry();

        //Update standard fields from bsProfile
        BsProfile bsProfile = user.getBsProfile();
        if (bsProfile != null) {
            this.region = bsProfile.getRegion();

            //Update standard fields from player
            Player player = bsProfile.getPlayer();
            if (player != null) {
                this.trophies = player.getTrophies();
                this.threeVThreeWins = player.getThreeVsThreeVictories();
                findAndUpdateBestBrawler(player);
            }
        }
    }
}

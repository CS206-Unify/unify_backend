package com.cs206.g2t2.models.brawlStarsAPI.playerInfo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Player {
    @SerializedName("tag")
    private String tag;

    @SerializedName("name")
    private String name;

    @SerializedName("nameColor")
    private String nameColor;

    @SerializedName("icon")
    private Icon icon;

    @SerializedName("trophies")
    private int trophies;

    @SerializedName("highestTrophies")
    private int highestTrophies;

    @SerializedName("highestPowerPlayPoints")
    private int highestPowerPlayPoints;

    @SerializedName("expLevel")
    private int expLevel;

    @SerializedName("expPoints")
    private int expPoints;

    @SerializedName("isQualifiedFromChampionshipChallenge")
    private boolean isQualifiedFromChampionshipChallenge;

    @SerializedName("3vs3Victories")
    private int threeVsThreeVictories;

    @SerializedName("soloVictories")
    private int soloVictories;

    @SerializedName("duoVictories")
    private int duoVictories;

    @SerializedName("bestRoboRumbleTime")
    private int bestRoboRumbleTime;

    @SerializedName("bestTimeAsBigBrawler")
    private int bestTimeAsBigBrawler;

    @SerializedName("club")
    private Club club;

    @SerializedName("brawlers")
    private List<Brawler> brawlers;
}

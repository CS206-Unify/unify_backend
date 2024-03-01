package com.cs206.g2t2.models.brawlStarsAPI.battleLog;

import com.cs206.g2t2.models.brawlStarsAPI.playerInfo.Brawler;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TeamPlayer {

    @SerializedName("tag")
    private String tag;

    @SerializedName("name")
    private String name;

    @SerializedName("brawler")
    private Brawler brawler;
}


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
public class Brawler {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("power")
    private int power;

    @SerializedName("rank")
    private int rank;

    @SerializedName("trophies")
    private int trophies;

    @SerializedName("highestTrophies")
    private int highestTrophies;

    @SerializedName("gears")
    private List<Gear> gears;

    @SerializedName("starPowers")
    private List<StarPower> starPowers;

    @SerializedName("gadgets")
    private List<Gadget> gadgets;
}

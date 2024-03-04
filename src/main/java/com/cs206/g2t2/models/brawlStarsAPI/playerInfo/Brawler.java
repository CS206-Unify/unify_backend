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
public class Brawler implements Comparable<Brawler>{

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


    @Override
    public int compareTo(Brawler o) {
        int diff = o.rank - this.rank;
        if (diff != 0) { return diff; }
        diff = o.trophies - this.trophies;
        if (diff != 0) { return diff; }
        diff = o.highestTrophies - this.highestTrophies;
        if (diff != 0) { return diff; }
        return (int)(this.getId() - o.getId());
    }
}

package com.cs206.g2t2.models.brawlStarsAPI.battleLog;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Event {

    @SerializedName("id")
    private long id;

    @SerializedName("mode")
    private String mode;

    @SerializedName("map")
    private String map;
}

